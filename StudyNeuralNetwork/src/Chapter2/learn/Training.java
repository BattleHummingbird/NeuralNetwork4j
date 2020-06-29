package Chapter2.learn;

import java.util.ArrayList;

import Chapter2.InputLayer;
import Chapter2.NeuralNet;
import Chapter2.Neuron;

public abstract class Training {

	private int epochs;		// 用来存储训练周期的次数
	private double error;	// 存储预期输出和实际输出的误差值
	private double mse;		// 存储均方误差误差的实数（MSE）

	//存储项目支持的训练类型（Perceptron和Adaline）
	public enum TrainingTypesENUM {
		PERCEPTRON, ADALINE;
	}

	/**
	 * 训练神经网络
	 * @param n 未训练的神经网络对象
	 * @return 训练过的神经网络
	 */
	public NeuralNet train(NeuralNet n) {
		
		ArrayList<Double> inputWeightIn = new ArrayList<Double>();

		int rows = n.getTrainSet().length;
		int cols = n.getTrainSet()[0].length;

		while (this.getEpochs() < n.getMaxEpochs()) {

			double estimatedOutput = 0.0;
			double realOutput = 0.0;

			for (int i = 0; i < rows; i++) {

				double netValue = 0.0;

				for (int j = 0; j < cols; j++) {
					inputWeightIn = n.getInputLayer().getListOfNeurons().get(j)
							.getListOfWeightIn();
					double inputWeight = inputWeightIn.get(0);
					netValue = netValue + inputWeight * n.getTrainSet()[i][j];
				}

				estimatedOutput = this.activationFnc(n.getActivationFnc(),
						netValue);
				realOutput = n.getRealOutputSet()[i];

				this.setError(realOutput - estimatedOutput);

				// System.out.println("Epoch: "+this.getEpochs()+" / Error: " + this.getError());

				if (Math.abs(this.getError()) > n.getTargetError()) {
					// fix weights
					InputLayer inputLayer = new InputLayer();
					inputLayer.setListOfNeurons(this.teachNeuronsOfLayer(cols,
							i, n, netValue));
					n.setInputLayer(inputLayer);
				}

			}

			this.setMse(Math.pow(realOutput - estimatedOutput, 2.0));
			n.getListOfMSE().add(this.getMse());

			this.setEpochs(this.getEpochs() + 1);

		}

		n.setTrainingError(this.getError());

		return n;
	}
	
	/**
	 *  使某层的神经元计算并改变其权值
	 * @param numberOfInputNeurons 	输入神经元的数量
	 * @param line					样本数量
	 * @param n						NeuralNet对象
	 * @param netValue				神经网络净输出
	 * @return
	 */
	private ArrayList<Neuron> teachNeuronsOfLayer(int numberOfInputNeurons,
			int line, NeuralNet n, double netValue) {
		ArrayList<Neuron> listOfNeurons = new ArrayList<Neuron>();
		ArrayList<Double> inputWeightsInNew = new ArrayList<Double>();
		ArrayList<Double> inputWeightsInOld = new ArrayList<Double>();

		for (int j = 0; j < numberOfInputNeurons; j++) {
			inputWeightsInOld = n.getInputLayer().getListOfNeurons().get(j)
					.getListOfWeightIn();
			double inputWeightOld = inputWeightsInOld.get(0);

			inputWeightsInNew.add( this.calcNewWeight(n.getTrainType(),
					inputWeightOld, n, this.getError(),
					n.getTrainSet()[line][j], netValue) );

			Neuron neuron = new Neuron();
			neuron.setListOfWeightIn(inputWeightsInNew);
			listOfNeurons.add(neuron);
			inputWeightsInNew = new ArrayList<Double>();
		}

		return listOfNeurons;

	}

	/**
	 * 计算某个神经元的新权值
	 * @param trainType			训练类型
	 * @param inputWeightOld	老的输入权值
	 * @param n					NeuralNet对象
	 * @param error				误差值
	 * @param trainSample		训练样本值
	 * @param netValue			净输出值
	 * @return	新权值
	 */
	private double calcNewWeight(TrainingTypesENUM trainType,
			double inputWeightOld, NeuralNet n, double error,
			double trainSample, double netValue) {
		switch (trainType) {
		case PERCEPTRON:
			return inputWeightOld + n.getLearningRate() * error * trainSample;
		case ADALINE:
			return inputWeightOld + n.getLearningRate() * error * trainSample
					* derivativeActivationFnc(n.getActivationFnc(), netValue);
		default:
			throw new IllegalArgumentException(trainType
					+ " does not exist in TrainingTypesENUM");
		}
	}

	// 存储项目支持的激活函数类型（阶跃、线性、sigmoid logistics、双曲正切）
	public enum ActivationFncENUM {
		STEP, LINEAR, SIGLOG, HYPERTAN;
	}

	/**
	 * 决定要使用的激活函数，并调用其计算方法
	 * @param fnc		激活函数
	 * @param value		实数值
	 * @return			激活函数的计算结果
	 */
	private double activationFnc(ActivationFncENUM fnc, double value) {
		switch (fnc) {
		case STEP:
			return fncStep(value);
		case LINEAR:
			return fncLinear(value);
		case SIGLOG:
			return fncSigLog(value);
		case HYPERTAN:
			return fncHyperTan(value);
		default:
			throw new IllegalArgumentException(fnc
					+ " does not exist in ActivationFncENUM");
		}
	}

	/**
	 * 决定选择哪个激活函数并调用计算其导数的方法
	 * @param fnc		激活函数
	 * @param value		实数值
	 * @return			激活函数的导数的计算结果
	 */
	public double derivativeActivationFnc(ActivationFncENUM fnc, double value) {
		switch (fnc) {
		case LINEAR:
			return derivativeFncLinear(value);
		case SIGLOG:
			return derivativeFncSigLog(value);
		case HYPERTAN:
			return derivativeFncHyperTan(value);
		default:
			throw new IllegalArgumentException(fnc
					+ " does not exist in ActivationFncENUM");
		}
	}

	/**
	 * 阶跃函数
	 * @param v
	 * @return
	 */
	private double fncStep(double v) {
		if (v >= 0) {
			return 1.0;
		} else {
			return 0.0;
		}
	}
	
	/**
	 * 线性函数
	 * @param v
	 * @return
	 */
	private double fncLinear(double v) {
		return v;
	}
	
	/**
	 * sigmoid logistics函数
	 * @param v
	 * @return
	 */
	private double fncSigLog(double v) {
		return 1.0 / (1.0 + Math.exp(-v));
	}
	
	/**
	 * 双曲正切函数
	 * @param v
	 * @return
	 */
	private double fncHyperTan(double v) {
		return Math.tanh(v);
	}

	/**
	 * 线性函数的导数
	 * @param v
	 * @return
	 */
	private double derivativeFncLinear(double v) {
		return 1.0;
	}
	
	/**
	 * sigmoid logistics函数的导数
	 * @param v
	 * @return
	 */
	private double derivativeFncSigLog(double v) {
		return v * (1.0 - v);
	}
	
	/**
	 * 双曲正切函数的导数
	 * @param v
	 * @return
	 */
	private double derivativeFncHyperTan(double v) {
		return (1.0 / Math.pow(Math.cosh(v), 2.0));
	}

	/**
	 * 输出训练过的神经网络并显示结果
	 * @param trainedNet
	 */
	public void printTrainedNetResult(NeuralNet trainedNet) {

		int rows = trainedNet.getTrainSet().length;
		int cols = trainedNet.getTrainSet()[0].length;

		ArrayList<Double> inputWeightIn = new ArrayList<Double>();

		for (int i = 0; i < rows; i++) {
			double netValue = 0.0;
			for (int j = 0; j < cols; j++) {
				inputWeightIn = trainedNet.getInputLayer().getListOfNeurons()
						.get(j).getListOfWeightIn();
				double inputWeight = inputWeightIn.get(0);
				netValue = netValue + inputWeight
						* trainedNet.getTrainSet()[i][j];

				System.out.print(trainedNet.getTrainSet()[i][j] + "\t");
			}

			double estimatedOutput = this.activationFnc(
					trainedNet.getActivationFnc(), netValue);

			System.out.print(" NET OUTPUT: " + estimatedOutput + "\t");
			System.out.print(" REAL OUTPUT: "
					+ trainedNet.getRealOutputSet()[i] + "\t");
			double error = estimatedOutput - trainedNet.getRealOutputSet()[i];
			System.out.print(" ERROR: " + error + "\n");

		}

	}

	/**
	 * 获取训练的迭代次数
	 * @return
	 */
	public int getEpochs() {
		return epochs;
	}

	/**
	 * 设置训练的迭代次数
	 * @param epochs
	 */
	public void setEpochs(int epochs) {
		this.epochs = epochs;
	}

	/**
	 * 获取训练误差值(估计值与真实值比较)
	 * @return
	 */
	public double getError() {
		return error;
	}

	/**
	 * 设置训练误差
	 * @param error
	 */
	public void setError(double error) {
		this.error = error;
	}

	/**
	 * 获取MSE
	 * @return
	 */
	public double getMse() {
		return mse;
	}

	/**
	 * 设置MSE
	 * @param mse
	 */
	public void setMse(double mse) {
		this.mse = mse;
	}

}
