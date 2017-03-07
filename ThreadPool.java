package gu;

public class ThreadPool {

	
	private class Buffer<T>{
		
	}
	private class Worker extends Thread{
		public void run(){
			while(!Thread.interrupted()){
				buffer.get().run();
			}catch (InterruptedException interruptedException){
				interruptedException.printStackTrace();
				break;
			}
		}
	}
}
