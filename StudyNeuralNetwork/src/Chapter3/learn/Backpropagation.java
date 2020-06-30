package Chapter3.learn;

import java.util.ArrayList;

import Chapter3.HiddenLayer;
import Chapter3.NeuralNet;
import Chapter3.Neuron;


public class Backpropagation extends Training {
	
	/**
	 * 使用反向传播算法训练神经网络
	 */
	public NeuralNet train(NeuralNet n) {
		
		int epoch = 0;
		
		setMse(1.0);	// 设置均方误差
		
		while(getMse() > n.getTargetError()) {	// 训练的停止条件：MSE小于目标方差
			if ( epoch >= n.getMaxEpochs() ) 	// 另一个跳出条件：迭代次数达到最大迭代次数
				break;
			int rows = n.getTrainSet().length;
			double sumErrors = 0.0;
			
			// 循环遍历训练集的每一个数据点
			for (int rows_i = 0; rows_i < rows; rows_i++) {
				n = forward(n, rows_i);
				n = backpropagation(n, rows_i);
				sumErrors = sumErrors + n.getErrorMean();
			}
			
			setMse( sumErrors / rows );
			System.out.println( getMse() );
			epoch++;
		}
		
		System.out.println("Number of epochs: "+epoch);
		return n;
	}

	/**
	 * 从第一层开始传播信号到隐藏层再到输出层
	 * @param n     NeuralNet对象
	 * @param row	训练集的行号
	 * @return
	 */
	protected NeuralNet forward(NeuralNet n, int row) {
		
		ArrayList<HiddenLayer> listOfHiddenLayer = new ArrayList<HiddenLayer>();

		listOfHiddenLayer = n.getListOfHiddenLayer(); // 未训练神经网络的隐藏层集合

		double estimatedOutput = 0.0;	// 估计输出
		double realOutput = 0.0;		// 实际输出
		double sumError = 0.0; 			// 误差总和
		
		if (listOfHiddenLayer.size() > 0) {
			
			int hiddenLayer_i = 0;
			
			// 遍历每个隐藏层
			for (HiddenLayer hiddenLayer : listOfHiddenLayer) {
				
				// 遍历隐藏层的神经元
				int numberOfNeuronsInLayer = hiddenLayer.getNumberOfNeuronsInLayer(); // 隐藏层神经元个数
				for (Neuron neuron : hiddenLayer.getListOfNeurons()) {
					
					double netValueOut = 0.0;
					
					if(neuron.getListOfWeightIn().size() > 0) {  // exclude bias  输入权值数量需要大于 1，去除训练时增加的偏置神经元
						double netValue = 0.0;
						
						// 输入权值的总和
						for (int layer_j = 0; layer_j < numberOfNeuronsInLayer - 1; layer_j++) { //exclude bias
							double hiddenWeightIn = neuron.getListOfWeightIn().get(layer_j);
							netValue = netValue + hiddenWeightIn * n.getTrainSet()[row][layer_j];
						}
						
						//output hidden layer (1) 使用激活函数计算神经元的输出结果
						netValueOut = super.activationFnc(n.getActivationFnc(), netValue);
						neuron.setOutputValue( netValueOut );
					} else {
						neuron.setOutputValue( 1.0 );
					}
					
				}
				
				// 遍历输出层神经元
				//output hidden layer (2)
				for (int outLayer_i = 0; outLayer_i < n.getOutputLayer().getNumberOfNeuronsInLayer(); outLayer_i++){
					double netValue = 0.0;
					double netValueOut = 0.0;
					
					for (Neuron neuron : hiddenLayer.getListOfNeurons()) {
						double hiddenWeightOut = neuron.getListOfWeightOut().get(outLayer_i);
						netValue = netValue + hiddenWeightOut * neuron.getOutputValue();
					}
					
					netValueOut = activationFnc(n.getActivationFncOutputLayer(), netValue);
					
					n.getOutputLayer().getListOfNeurons().get(outLayer_i).setOutputValue( netValueOut );
					
					//error 误差计算
					estimatedOutput = netValueOut;
					realOutput = n.getRealMatrixOutputSet()[row][outLayer_i];
					double error = realOutput - estimatedOutput;
					n.getOutputLayer().getListOfNeurons().get(outLayer_i).setError(error);
					sumError = sumError + Math.pow(error, 2.0);
					
				}
				
				//error mean
				double errorMean = sumError / n.getOutputLayer().getNumberOfNeuronsInLayer();
				n.setErrorMean(errorMean);
				
				n.getListOfHiddenLayer().get(hiddenLayer_i).setListOfNeurons( hiddenLayer.getListOfNeurons() );
			
				hiddenLayer_i++;
				
			}
			
		}

		return n;
		
	}

	/**
	 * 从输出层反向传播信号到隐藏层再到第一层。在方法中，权值会被调整
	 * @param n
	 * @param row	训练集的行号
	 * @return
	 */
	protected NeuralNet backpropagation(NeuralNet n, int row) {

		// 1.初始化训练参数并检索神经网络层（隐藏层和输出层）
		ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
		outputLayer = n.getOutputLayer().getListOfNeurons();	// 输出层神经元集合
		
		ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
		hiddenLayer = n.getListOfHiddenLayer().get(0).getListOfNeurons();	// 第一层隐藏层神经元集合（因为假设是只有简单一层隐藏层）
		
		double error = 0.0;
		double netValue = 0.0;
		double sensibility = 0.0;	// 敏感性
		
		//sensibility output layer 
		// 2.计算输出层的敏感性
		for (Neuron neuron : outputLayer) {
			error = neuron.getError();
			netValue = neuron.getOutputValue();
			sensibility = derivativeActivationFnc(n.getActivationFncOutputLayer(), netValue) * error;
			
			neuron.setSensibility(sensibility);
		}
		
		n.getOutputLayer().setListOfNeurons(outputLayer);
		
		
		//sensibility hidden layer
		// 3.计算隐藏层的敏感性
		for (Neuron neuron : hiddenLayer) {
			
			sensibility = 0.0;
			
			if(neuron.getListOfWeightIn().size() > 0) { //exclude bias
				ArrayList<Double> listOfWeightsOut = new ArrayList<Double>();
				
				listOfWeightsOut = neuron.getListOfWeightOut();
				
				double tempSensibility = 0.0;
				
				int weight_i = 0;
				for (Double weight : listOfWeightsOut) {
					tempSensibility = tempSensibility + (weight * outputLayer.get(weight_i).getSensibility());
					weight_i++;
				}
				
				sensibility = derivativeActivationFnc(n.getActivationFnc(), neuron.getOutputValue()) * tempSensibility;
				
				neuron.setSensibility(sensibility);
				
			}
			
		}
		
		//fix weights (teach) [output layer to hidden layer]
		// 4.更新输出层的权值
		for (int outLayer_i = 0; outLayer_i < n.getOutputLayer().getNumberOfNeuronsInLayer(); outLayer_i++) {
			
			for (Neuron neuron : hiddenLayer) {
				
				double newWeight = neuron.getListOfWeightOut().get( outLayer_i ) + 
						( n.getLearningRate() * outputLayer.get( outLayer_i ).getSensibility() * 
								  neuron.getOutputValue() );
				
				neuron.getListOfWeightOut().set(outLayer_i, newWeight);
			}
			
		}
		
		//fix weights (teach) [hidden layer to input layer]
		// 5.更新隐藏层的权值
		for (Neuron neuron : hiddenLayer) {
			
			ArrayList<Double> hiddenLayerInputWeights = new ArrayList<Double>();
			hiddenLayerInputWeights = neuron.getListOfWeightIn();
			
			if(hiddenLayerInputWeights.size() > 0) { //exclude bias
			
				int hidden_i = 0;
				double newWeight = 0.0;
				for (int i = 0; i < n.getInputLayer().getNumberOfNeuronsInLayer(); i++) {
					
					newWeight = hiddenLayerInputWeights.get(hidden_i) +
							( n.getLearningRate() * neuron.getSensibility() * 
							  n.getTrainSet()[row][i]); 
					
					neuron.getListOfWeightIn().set(hidden_i, newWeight);
					
					hidden_i++;
				}
				
			}
			
		}
		
		n.getListOfHiddenLayer().get(0).setListOfNeurons(hiddenLayer);

		return n;
		
	}

}
