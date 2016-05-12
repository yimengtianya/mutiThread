package com.train.cyclicBarrier;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCyclicBarrier {

	// 徒步需要的时间: Shenzhen, Guangzhou, Shaoguan, Changsha, Wuhan

	private static int[] timeWalk = { 5, 8, 15, 15, 10 };

	// 自驾游

	private static int[] timeSelf = { 1, 3, 4, 4, 5 };

	// 旅游大巴

	private static int[] timeBus = { 2, 4, 6, 6, 7 };

	static String now() {

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		return sdf.format(new Date()) + ": ";

	}

	static class Tour implements Runnable {

		private int[] times;

		private CyclicBarrier barrier;

		private String tourName;

		public Tour(CyclicBarrier barrier, String tourName, int[] times) {

			this.times = times;

			this.tourName = tourName;

			this.barrier = barrier;

		}
		

		public void run() {

			try {

				Thread.sleep(times[0] * 1000);

				System.out.println(now() + tourName + " Reached Shenzhen");

				int index = barrier.await();
				System.out.println(index + tourName + " Reached Shenzhen");

				Thread.sleep(times[1] * 1000);

				System.out.println(now() + tourName + " Reached Guangzhou");

				index = barrier.await();
				System.out.println(index + tourName + " Reached Guangzhou");
				
				Thread.sleep(times[2] * 1000);

				System.out.println(now() + tourName + " Reached Shaoguan");

				index = barrier.await();
				System.out.println(index + tourName + " Reached Shaoguan");
				Thread.sleep(times[3] * 1000);

				System.out.println(now() + tourName + " Reached Changsha");

				index = barrier.await();
				System.out.println(index + tourName + " Reached Changsha");
				Thread.sleep(times[4] * 1000);

				System.out.println(now() + tourName + " Reached Wuhan");

				index = barrier.await();
				System.out.println(index + tourName + " Reached Wuhan");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}

		}
	}

	public static void main(String[] args) {

		// 三个旅行团

		CyclicBarrier barrier = new CyclicBarrier(3);

//		ExecutorService exec = Executors.newFixedThreadPool(3);
//
//		exec.submit(new Tour(barrier, "WalkTour", timeWalk));
//
//		exec.submit(new Tour(barrier, "SelfTour", timeSelf));
//
//		exec.submit(new Tour(barrier, "BusTour", timeBus));
//
//		exec.shutdown();
		
		new Thread(new Tour(barrier, "WalkTour", timeWalk)).start();
		new Thread(new Tour(barrier, "SelfTour", timeSelf)).start();
		new Thread(new Tour(barrier, "BusTour", timeBus)).start();
	}
}