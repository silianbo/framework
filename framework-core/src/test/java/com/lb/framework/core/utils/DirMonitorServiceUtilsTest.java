package com.lb.framework.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent.Kind;
import java.util.Properties;

import com.lb.framework.core.utils.DirMonitorServiceUtils.AbstractFileMonitor;

public class DirMonitorServiceUtilsTest {

	public static void main(String[] args) throws URISyntaxException {
		new DirMonitorServiceUtilsTest().test();
	}
	
	//@Test
	public void test() throws URISyntaxException {
		//testRegisterAndClose();
		
		TestAbstractFileMonitor monitor2 = new TestAbstractFileMonitor();
		Path path = Paths.get(DirMonitorServiceUtils.class.getClassLoader().getResource("").toURI());
		System.out.println(path);
		DirMonitorServiceUtils.watch(path, monitor2);
	}
	private void testRegisterAndClose() throws URISyntaxException {
		TestAbstractFileMonitor monitor2 = new TestAbstractFileMonitor();
		Path path = Paths.get(DirMonitorServiceUtils.class.getClassLoader().getResource("./META-INF").toURI());
		DirMonitorServiceUtils.watch(path, monitor2);

		File file2 = new File(path.toAbsolutePath().toString() + "/abcdef.properties");
		
		if (file2.exists()) {
			file2.delete();
			sleep100();
			System.out.println("after delete");
		}
		
		try {
			file2.createNewFile();
			sleep100();
			System.out.println("after create file");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		monitor2.setClosed(true);
		System.out.println("set close");
		sleep100();
		changeFile(file2);
		sleep100();
		System.out.println("after set closed");
		System.out.println("change 1 ");
		changeFile(file2);
		sleep100();
		System.out.println("after set closed");
		System.out.println("change 2 ");
		
		changeFile(file2);
		sleep100();
		System.out.println("after set closed");
		System.out.println("change 3 ");
		
		sleep100();
		System.out.println("rewatch ");
		DirMonitorServiceUtils.watch(path, monitor2);
		sleep100();
		changeFile(file2);
		sleep100();
		System.out.println("after rewatch");
		System.out.println("change 4 ");
		
		monitor2.setClosed(true);
		System.out.println("set close");
		sleep100();
		changeFile(file2);
		sleep100();
		System.out.println("after set closed");
		System.out.println("change 5 ");
		
		int count = 5; 
		while (count < 10) {
			//try {
			//	System.in.read();
				sleep60000();
				
				sleep100();
				System.out.println("rewatch ");
				DirMonitorServiceUtils.watch(path, monitor2);
				sleep100();
				changeFile(file2);
				sleep100();
				System.out.println("after rewatch");
				System.out.println("change "+ (++count));
				

				monitor2.setClosed(true);
				System.out.println("set close");
				sleep100();
				changeFile(file2);
				sleep100();
				System.out.println("after set closed");
				System.out.println("change "+ (++count));
		}
	}
	private static void sleep60000(){
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	private static void sleep100(){
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void changeFile(File file2) {
		Properties properties = null;
		properties = new Properties();
		try (FileInputStream fis = new FileInputStream(file2); FileOutputStream fos = new FileOutputStream(file2);) {
			properties.load(fis);
			properties.put("abc", "def");
			properties.store(fos, "hello world");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static class TestAbstractFileMonitor extends AbstractFileMonitor{
		private boolean isClosed = false; 
		int count = 0; 
		public void setClosed(boolean isClosed) {
			this.isClosed = isClosed;
		}
		@Override
		public boolean isClosed() {
			return isClosed;
		}
		public boolean match(String fileName, String fileAbsolutePath, Kind<Path> kind){
			return true; 
		}
		public void process(Path file, Path resolvePath, Kind<Path> eventKind){
			System.out.println(Thread.currentThread().getName() + ": " + file + "  " + resolvePath + "  " + eventKind.name());
		}
	}
}
