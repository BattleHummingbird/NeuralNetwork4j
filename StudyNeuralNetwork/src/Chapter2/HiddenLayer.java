package Chapter2;

import java.util.ArrayList;
import java.util.Arrays;

public class HiddenLayer extends Layer {

	/**
	 * 用伪随机实数初始化隐藏层
	 * @param hiddenLayer 一个HiddenLayer类的对象
	 * @param listOfHiddenLayer 一个元素类型为HiddenLayer的集合
	 * @param inputLayer 一个InputLayer类的对象
	 * @param outputLayer 一个OutputLayer类的对象
	 * @return
	 */
	public ArrayList<HiddenLayer> initLayer(HiddenLayer hiddenLayer, ArrayList<HiddenLayer> listOfHiddenLayer, InputLayer inputLayer, OutputLayer outputLayer) {

		ArrayList<Double> listOfWeightIn = new ArrayList<Double>(); 	// 输入权重集合
		ArrayList<Double> listOfWeightOut = new ArrayList<Double>();	// 输出权重集合
		ArrayList<Neuron> listOfNeurons = new ArrayList<Neuron>();		// 神经元集合

		int numberOfHiddenLayers = listOfHiddenLayer.size();			// 隐藏层集合数量

		for (int i = 0; i < numberOfHiddenLayers; i++) {
			for (int j = 0; j < hiddenLayer.getNumberOfNeuronsInLayer(); j++) {
				Neuron neuron = new Neuron();

				int limitIn;
				int limitOut;

				if (i == 0) { // first  第一层隐藏层
					limitIn = inputLayer.getNumberOfNeuronsInLayer(); 	// 设置为输入层的神经元个数
					if (numberOfHiddenLayers > 1) {	
						limitOut = listOfHiddenLayer.get(i + 1).getNumberOfNeuronsInLayer(); // 设置为下一隐藏层的神经元个数
					} else {
						limitOut = listOfHiddenLayer.get(i).getNumberOfNeuronsInLayer(); 
					}
				} else if (i == numberOfHiddenLayers - 1) { // last 最后一层隐藏层
					limitIn = listOfHiddenLayer.get(i - 1).getNumberOfNeuronsInLayer();
					limitOut = outputLayer.getNumberOfNeuronsInLayer();
				} else { // middle 中间隐藏层
					limitIn = listOfHiddenLayer.get(i - 1).getNumberOfNeuronsInLayer();
					limitOut = listOfHiddenLayer.get(i + 1).getNumberOfNeuronsInLayer();
				}

				for (int k = 0; k < limitIn; k++) {
					listOfWeightIn.add(neuron.initNeuron());	//设置输入权重
				}
				for (int k = 0; k < limitOut; k++) {
					listOfWeightOut.add(neuron.initNeuron());	//设置输出权重
				}

				neuron.setListOfWeightIn(listOfWeightIn);
				neuron.setListOfWeightOut(listOfWeightOut);
				listOfNeurons.add(neuron);

				listOfWeightIn = new ArrayList<Double>();
				listOfWeightOut = new ArrayList<Double>();

			}

			listOfHiddenLayer.get(i).setListOfNeurons(listOfNeurons);

			listOfNeurons = new ArrayList<Neuron>();

		}

		return listOfHiddenLayer;

	}

	/**
	 * 打印隐藏层的权重
	 * @param listOfHiddenLayer
	 */
	public void printLayer(ArrayList<HiddenLayer> listOfHiddenLayer) {
		System.out.println("### HIDDEN LAYER ###");
		int h = 1;
		for (HiddenLayer hiddenLayer : listOfHiddenLayer) {
			System.out.println("Hidden Layer #" + h);
			int n = 1;
			for (Neuron neuron : hiddenLayer.getListOfNeurons()) {
				System.out.println("Neuron #" + n);
				System.out.println("Input Weights:");
				System.out.println(Arrays.deepToString( neuron.getListOfWeightIn().toArray() ));
				System.out.println("Output Weights:");
				System.out.println(Arrays.deepToString( neuron.getListOfWeightOut().toArray() ));
				n++;
			}
			h++;
		}
	}
}
