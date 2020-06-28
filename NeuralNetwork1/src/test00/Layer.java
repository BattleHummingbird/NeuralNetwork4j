package test00;

import java.util.ArrayList;

public abstract class Layer {
	
	private ArrayList<Neuron> listOfNeurons; //一个ArrayList变量，元素类型为Neuron类
	private int numberOfNeuronsInLayer;		 //用来 储存 层的神经元数量
	
	/***
	 * 返回层的神经元集合
	 * @param None
	 * @return 一个ArrayList变量，元素类型为Neuron类
	 */
	public ArrayList<Neuron> getListOfNeurons() {
		return listOfNeurons;
	}
	
	/***
	 * 用一个Neuron集合设置listOfNeurons的函数
	 * @param 将被存储的Neuron集合
	 * @return None
	 */
	public void setListOfNeurons(ArrayList<Neuron> listOfNeurons) {
		this.listOfNeurons = listOfNeurons;
	}
	
	/**
	 * 返回层的神经元的数量
	 * @param None
	 * @return 层的神经元数量
	 */
	public int getNumberOfNeuronsInLayer() {
		return numberOfNeuronsInLayer;
	}

	/**
	 * 设置层的神经元的数量
	 * @param 层的神经元数量
	 * @return None
	 */
	public void setNumberOfNeuronsInLayer(int numberOfNeuronsInLayer) {
		this.numberOfNeuronsInLayer = numberOfNeuronsInLayer;
	}
	
	
}
