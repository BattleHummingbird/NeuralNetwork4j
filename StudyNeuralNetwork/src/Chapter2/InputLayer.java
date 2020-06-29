package Chapter2;

import java.util.ArrayList;
import java.util.Arrays;

public class InputLayer extends Layer {

	/**
	 * 用伪随机实数初始化输入层
	 * @param inputLayer
	 * @return
	 */
	public InputLayer initLayer(InputLayer inputLayer) {
		
		// 输入层只“输入”权值
		ArrayList<Double> listOfWeightInTemp = new ArrayList<Double>();
		ArrayList<Neuron> listOfNeurons = new ArrayList<Neuron>();	//输入层所含神经元集合
		
		for (int i = 0; i < inputLayer.getNumberOfNeuronsInLayer(); i++) {
			Neuron neuron = new Neuron();
			
			listOfWeightInTemp.add( neuron.initNeuron() );

			neuron.setListOfWeightIn( listOfWeightInTemp );
			listOfNeurons.add( neuron );

			listOfWeightInTemp = new ArrayList<Double>();
		}

		inputLayer.setListOfNeurons(listOfNeurons);

		return inputLayer;
	}

	/**
	 * 打印输入层权值
	 * @param inputLayer
	 */
	public void printLayer(InputLayer inputLayer){
		System.out.println("### INPUT LAYER ###");
		int n = 1;
		for (Neuron neuron : inputLayer.getListOfNeurons()) {
			System.out.println("Neuron #" + n + ":");
			System.out.println("Input Weights:");
			System.out.println(Arrays.deepToString( neuron.getListOfWeightIn().toArray() ));
			n++;
		}
	}
	
	// 设置输入层的神经元数量，由于偏置的存在，它是一个一个增加的
	public void setNumberOfNeuronsInLayer(int numberOfNeuronsInLayer) {
		this.numberOfNeuronsInLayer = numberOfNeuronsInLayer + 1; //BIAS
	}
}
