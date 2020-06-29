package Chapter2;

import java.util.ArrayList;

public abstract class Layer {

	private ArrayList<Neuron> listOfNeurons;  // 每层所含神经元集合
	protected int numberOfNeuronsInLayer;		  // 神经元数量
	
	public void printLayer(){
	}

	/**
	 * 返回该层神经元集合
	 * @return listOfNeurons
	 */
	public ArrayList<Neuron> getListOfNeurons() {
		return listOfNeurons;
	}

	/**
	 * 设置该层神经元集合
	 * @param listOfNeurons
	 */
	public void setListOfNeurons(ArrayList<Neuron> listOfNeurons) {
		this.listOfNeurons = listOfNeurons;
	}

	/**
	 * 获取该层神经元数量
	 * @return numberOfNeuronsInLayer
	 */
	public int getNumberOfNeuronsInLayer() {
		return numberOfNeuronsInLayer;
	}

	/**
	 * 设置该层神经元数量
	 * @param numberOfNeuronsInLayer
	 */
	public void setNumberOfNeuronsInLayer(int numberOfNeuronsInLayer) {
		this.numberOfNeuronsInLayer = numberOfNeuronsInLayer;
	}
	
	
	
	
}
