package org.xyc.showsome.pecan.thread;

public class ThreadA extends Thread {

    int count=0;

    public void run(){
        System.out.println(getName()+"��Ҫ����...");
        while(!this.isInterrupted()){
            System.out.println(getName()+"������"+count++);
            try{
                Thread.sleep(40000);
            }catch(InterruptedException e){
                System.out.println(getName()+"���������˳�...");
                System.out.println("ex before this.isInterrupted()="+this.isInterrupted());
                Thread.currentThread().interrupt();
                System.out.println("ex after this.isInterrupted()="+this.isInterrupted());
            }
        }
        System.out.println(getName()+"�Ѿ���ֹ!");
    }

    public static void main(String[] args) {
        try {
            ThreadA ta=new ThreadA();
            ta.setName("ThreadA");
            ta.start();
            Thread.sleep(2000);
            System.out.println(ta.getName()+"���ڱ��ж�...");
            System.out.println("before ta.isInterrupted()="+ta.isInterrupted());
            ta.interrupt();
            System.out.println("after ta.isInterrupted()="+ta.isInterrupted());
//            Thread.sleep(2000);
//            ta.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
