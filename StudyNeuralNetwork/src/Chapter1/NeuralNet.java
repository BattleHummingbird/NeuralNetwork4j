package Chapter1;

import java.util.ArrayList;

/**
 * 注意:神经网络拓扑结构在该类中是固定的(输出层有两个神经元，两个隐藏层，
 * 		每层分别有3个神经元，输出层有一个神经元)
 * 提示:这是第一个版本
 * @author kaede
 *
 */
public class NeuralNet {

	private InputLayer inputLayer;
	private HiddenLayer hiddenLayer;
	private ArrayList<HiddenLayer> listOfHiddenLayer;
	private OutputLayer outputLayer;
	private int numberOfHiddenLayers; // 隐藏层层数
	
	public void initNet(){
		// 创建输入层
		inputLayer = new InputLayer();
		inputLayer.setNumberOfNeuronsInLayer( 2 );
		
		// 创建隐藏层
		numberOfHiddenLayers = 2;
		listOfHiddenLayer = new ArrayList<HiddenLayer>();
		for (int i = 0; i < numberOfHiddenLayers; i++) {
			hiddenLayer = new HiddenLayer();
			hiddenLayer.setNumberOfNeuronsInLayer( 3 );
			listOfHiddenLayer.add( hiddenLayer );
		}
		
		// 创建输出层
		outputLayer = new OutputLayer();
		outputLayer.setNumberOfNeuronsInLayer( 1 );
		
		// 建立链接
		inputLayer = inputLayer.initLayer(inputLayer);
		listOfHiddenLayer = hiddenLayer.initLayer(hiddenLayer, listOfHiddenLayer, inputLayer, outputLayer);
		outputLayer = outputLayer.initLayer(outputLayer);
		
	}
	
	
	public void printNet(){
		inputLayer.printLayer(inputLayer);
		System.out.println();
		hiddenLayer.printLayer(listOfHiddenLayer);
		System.out.println();
		outputLayer.printLayer(outputLayer);
	}
	
}
