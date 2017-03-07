package gu;

import java.util.LinkedList;

public class ThreadPool {
	private Buffer<Runnable> buffer = new Buffer<Runnable>();
	private LinkedList<Worker> workerList;
	private int maxThreads;

	public ThreadPool(int maxThreads) {
		this.maxThreads = maxThreads;
	}

	public synchronized void start() {
		Worker worker;
		if(workerList == null){
			workerList = new LinkedList<Worker>();
			for(int i = 0; i < maxThreads; i++){
				worker = new Worker();
				worker.start();
				workerList.add(worker);
			}
		}

	}
	public void execute(Runnable runnable){
		buffer.put(runnable);
	}

	private class Buffer<T> {
		private LinkedList<T> buffer = new LinkedList<T>();

		public synchronized void put(T obj) {
			buffer.addLast(obj);
			notifyAll();
		}

		public synchronized T get() throws InterruptedException {
			while (buffer.isEmpty()) {
				wait();
			}
			return buffer.removeFirst();
		}

		public synchronized void clear() {
			buffer.clear();
		}

		public int size() {
			return buffer.size();
		}
	}

	private class Worker extends Thread {
		public void run() {
			while (!Thread.interrupted()) {
				try {
					buffer.get().run();
				} catch (InterruptedException interruptedException) {
					interruptedException.printStackTrace();
					break;
				}
			}
		}
	}
}
