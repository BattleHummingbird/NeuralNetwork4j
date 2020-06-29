package Chapter2;

import java.util.ArrayList;

import Chapter2.learn.Adaline;
import Chapter2.learn.Perceptron;
import Chapter2.learn.Training.ActivationFncENUM;
import Chapter2.learn.Training.TrainingTypesENUM;

public class NeuralNet {

	private InputLayer inputLayer;
	private HiddenLayer hiddenLayer;
	private ArrayList<HiddenLayer> listOfHiddenLayer;
	private OutputLayer outputLayer;
	private int numberOfHiddenLayers; // 隐藏层层数
	
	private double[][] trainSet;			// 存储输入数据的训练集矩阵
	private double[] realOutputSet;			// 存储输出数据的训练集向量
	private int maxEpochs;					// 存储神经网络训练的最大迭代次数
	private double learningRate;			// 存储学习率的实数
	private double targetError;				// 存储目标误差的实数
	private double trainingError;			// 存储训练误差的实数
	private ArrayList<Double> listOfMSE = new ArrayList<Double>();  // 存储每次迭代的MSE的实数数组
	private ActivationFncENUM activationFnc;	// 用于训练的激活函数枚举值
	private TrainingTypesENUM trainType;		// 训练神经网络的训练类型枚举值
	
	/**
	 * 初始化神经网络
	 * @param numberOfInputNeurons			输入层所含神经元个数
	 * @param numberOfHiddenLayers			隐藏层层数
	 * @param numberOfNeuronsInHiddenLayer	隐藏层所含神经元个数
	 * @param numberOfOutputNeurons			输出层所含神经元个数
	 * @return
	 */
	public NeuralNet initNet(int numberOfInputNeurons, 
			int numberOfHiddenLayers,
			int numberOfNeuronsInHiddenLayer,
			int numberOfOutputNeurons){
		inputLayer = new InputLayer();
		inputLayer.setNumberOfNeuronsInLayer( numberOfInputNeurons );
		
		listOfHiddenLayer = new ArrayList<HiddenLayer>();
		for (int i = 0; i < numberOfHiddenLayers; i++) {
			hiddenLayer = new HiddenLayer();
			hiddenLayer.setNumberOfNeuronsInLayer( numberOfNeuronsInHiddenLayer );
			listOfHiddenLayer.add( hiddenLayer );
		}
		
		outputLayer = new OutputLayer();
		outputLayer.setNumberOfNeuronsInLayer( numberOfOutputNeurons );
		
		inputLayer = inputLayer.initLayer(inputLayer);
		
		if(numberOfHiddenLayers > 0) {
			listOfHiddenLayer = hiddenLayer.initLayer(hiddenLayer, listOfHiddenLayer, inputLayer, outputLayer);
		}

		outputLayer = outputLayer.initLayer(outputLayer);
		
		NeuralNet newNet = new NeuralNet();
		newNet.setInputLayer(inputLayer);
		newNet.setHiddenLayer(hiddenLayer);
		newNet.setListOfHiddenLayer(listOfHiddenLayer);
		newNet.setNumberOfHiddenLayers(numberOfHiddenLayers);
		newNet.setOutputLayer(outputLayer);
	
		return newNet;
	}
	
	
	public void printNet(NeuralNet n){
		inputLayer.printLayer(n.getInputLayer());
		System.out.println();
		if(n.getHiddenLayer() != null){
			hiddenLayer.printLayer(n.getListOfHiddenLayer());
			System.out.println();
		}
		outputLayer.printLayer(n.getOutputLayer());
	}
	
	/**
	 * 训练神经网络
	 * @param n 未训练的神经网络
	 * @return	已训练的神经网络
	 */
	public NeuralNet trainNet(NeuralNet n){

		NeuralNet trainedNet = new NeuralNet();
		
		switch (n.trainType) {
			case PERCEPTRON:
				Perceptron t = new Perceptron();
				trainedNet = t.train(n);
				return trainedNet;
			case ADALINE:
				Adaline a = new Adaline();
				trainedNet = a.train(n);
				return trainedNet;
			default:
				throw new IllegalArgumentException(n.trainType+" does not exist in TrainingTypesENUM");
		}
		
	}
	
	/**
	 * 输出神经网络并显示其结果
	 * @param n	
	 */
	public void printTrainedNetResult(NeuralNet n) {
		switch (n.trainType) {
			case PERCEPTRON:
				Perceptron t = new Perceptron();
				t.printTrainedNetResult( n );
				break;
			case ADALINE:
				Adaline a = new Adaline();
				a.printTrainedNetResult( n );
				break;
			default:
				throw new IllegalArgumentException(n.trainType+" does not exist in TrainingTypesENUM");
		}
	}
	
	public InputLayer getInputLayer() {
		return inputLayer;
	}

	public void setInputLayer(InputLayer inputLayer) {
		this.inputLayer = inputLayer;
	}

	public HiddenLayer getHiddenLayer() {
		return hiddenLayer;
	}

	public void setHiddenLayer(HiddenLayer hiddenLayer) {
		this.hiddenLayer = hiddenLayer;
	}

	public ArrayList<HiddenLayer> getListOfHiddenLayer() {
		return listOfHiddenLayer;
	}

	public void setListOfHiddenLayer(ArrayList<HiddenLayer> listOfHiddenLayer) {
		this.listOfHiddenLayer = listOfHiddenLayer;
	}

	public OutputLayer getOutputLayer() {
		return outputLayer;
	}

	public void setOutputLayer(OutputLayer outputLayer) {
		this.outputLayer = outputLayer;
	}

	public int getNumberOfHiddenLayers() {
		return numberOfHiddenLayers;
	}

	public void setNumberOfHiddenLayers(int numberOfHiddenLayers) {
		this.numberOfHiddenLayers = numberOfHiddenLayers;
	}

	/**
	 * 获取输入数据的训练集矩阵
	 * @return
	 */
	public double[][] getTrainSet() {
		return trainSet;
	}

	/**
	 * 设置输入数据的训练集矩阵
	 * @param trainSet
	 */
	public void setTrainSet(double[][] trainSet) {
		this.trainSet = trainSet;
	}

	/**
	 * 获取输出数据的训练集向量
	 * @return
	 */
	public double[] getRealOutputSet() {
		return realOutputSet;
	}

	/**
	 * 设置输出数据的训练集向量
	 * @param realOutputSet
	 */
	public void setRealOutputSet(double[] realOutputSet) {
		this.realOutputSet = realOutputSet;
	}

	/**
	 * 获取神经网络将要训练的最大迭代次数
	 * @return
	 */
	public int getMaxEpochs() {
		return maxEpochs;
	}

	/**
	 * 设置神经网络将要训练的最大迭代次数
	 * @param maxEpochs
	 */
	public void setMaxEpochs(int maxEpochs) {
		this.maxEpochs = maxEpochs;
	}

	/**
	 * 获取目标误差
	 * @return
	 */
	public double getTargetError() {
		return targetError;
	}

	/**
	 * 设置目标误差
	 * @param targetError
	 */
	public void setTargetError(double targetError) {
		this.targetError = targetError;
	}

	/**
	 * 获取训练中使用的学习率
	 * @return
	 */
	public double getLearningRate() {
		return learningRate;
	}

	/**
	 * 设置训练中使用的学习率
	 * @param learningRate
	 */
	public void setLearningRate(double learningRate) {
		this.learningRate = learningRate;
	}

	/**
	 * 获取目标误差
	 * @return
	 */
	public double getTrainingError() {
		return trainingError;
	}

	/**
	 * 设置目标误差
	 * @param trainingError
	 */
	public void setTrainingError(double trainingError) {
		this.trainingError = trainingError;
	}

	/**
	 * 获取训练中使用的激活函数枚举值
	 * @return
	 */
	public ActivationFncENUM getActivationFnc() {
		return activationFnc;
	}

	/**
	 * 训练中使用的激活函数枚举值
	 * @param activationFnc
	 */
	public void setActivationFnc(ActivationFncENUM activationFnc) {
		this.activationFnc = activationFnc;
	}

	/**
	 * 获取训练中使用的训练类型枚举值
	 * @return
	 */
	public TrainingTypesENUM getTrainType() {
		return trainType;
	}

	/**
	 * 设置训练中使用的训练类型枚举值
	 * @param trainType
	 */
	public void setTrainType(TrainingTypesENUM trainType) {
		this.trainType = trainType;
	}

	/**
	 * 获取存储每次迭代的MES误差的实数列表
	 * @return
	 */
	public ArrayList<Double> getListOfMSE() {
		return listOfMSE;
	}

	/**
	 * 设置存储每次迭代的MES误差的实数列表
	 * @return
	 */
	public void setListOfMSE(ArrayList<Double> listOfMSE) {
		this.listOfMSE = listOfMSE;
	}
	
}

