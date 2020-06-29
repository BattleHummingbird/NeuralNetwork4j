package Chapter2;

import java.util.ArrayList;
import java.util.Random;

public class Neuron {

	private ArrayList<Double> listOfWeightIn; 	// 一个实数ArrayList变量，代表输入权值的集合
	private ArrayList<Double> listOfWeightOut;	// 一个实数ArrayList变量，代表输出权值的集合

	/**
	 * 用伪随机实数初始化 listOfWeightIn和listOfWeightOut
	 * @return 一个伪随机实数
	 */
	public double initNeuron(){
		Random r = new Random();
		return r.nextDouble();
	}
	
	/**
	 * 返回神经元集合的输入权值
	 * @return 存储在listOfWeightIn变量里的实数集合
	 */
	public ArrayList<Double> getListOfWeightIn() {
		return listOfWeightIn;
	}
	
	/**
	 * 用一个实数集合设置listofWeightIn
	 * @param listOfWeightIn 将被存储在类对象里的实数集合
	 */
	public void setListOfWeightIn(ArrayList<Double> listOfWeightIn) {
		this.listOfWeightIn = listOfWeightIn;
	}

	/**
	 * 返回神经元集合的输出权值
	 * @return 存储在listOfWeightOut变量里的实数集合
	 */
	public ArrayList<Double> getListOfWeightOut() {
		return listOfWeightOut;
	}

	/**
	 * 用一个实数集合设置listofWeightOut
	 * @param listOfWeightOut 将被存储在类对象里的实数集合
	 */
	public void setListOfWeightOut(ArrayList<Double> listOfWeightOut) {
		this.listOfWeightOut = listOfWeightOut;
	}
	
}
