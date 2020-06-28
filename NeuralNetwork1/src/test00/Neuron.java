package test00;

import java.util.ArrayList;
import java.util.Random;

public class Neuron {
	
	private ArrayList<Double> listOfWeightIn;    //一个实数ArrayList变量，代表输入权值的集合
	private ArrayList<Double> listOfWeightOut;	 //一个实数ArrayList变量，代表输出权值的集合
	
	/**
	 * 用伪随机实数初始化 listOfWeightIn 和 listOfWeightOut 
	 * @param None
	 * @return 一个伪随机实数
	 */
	public double initNeuron() {
		//创建一个伪随机实数
		Random rad=new Random(); 
		Double initD = rad.nextDouble();
		
		
		
		listOfWeightIn.add(initD);
		listOfWeightOut.add(initD);
		return initD;
	}
	
	/**
	 * 用一个实数集合设置 listOfWeightIn 的函数
	 * @param 将被存储在类对象的实数列表
	 * @return None
	 */
	public void setListOfWeightIn(ArrayList<Double> listOfWeightIn) {
		this.listOfWeightIn = listOfWeightIn;
	}
	
	/**
	 * 用一个实数集合设置 listOfWeightOut 的函数
	 * @param 将被存储在类对象的实数列表
	 * @return None
	 */
	public void setListOfWeightOut(ArrayList<Double> listOfWeightOut) {
		this.listOfWeightOut = listOfWeightOut;
	}
	
	/**
	 * 返回神经元集合的输入权值
	 * @param None
	 * @return 存储在 listOfWeightIn 变量里的实数集合
	 */
	public ArrayList<Double> getListOfWeightIn() {
		return listOfWeightIn;
	}
	
	/**
	 * 返回神经元集合的输出权值
	 * @param None
	 * @return 存储在 listOfWeightOut 变量里的实数集合
	 */
	public ArrayList<Double> getListOfWeightOut() {
		return listOfWeightOut;
	}
}
