package Chapter4.som;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Chapter4.NeuralNet;
import Chapter4.learn.Training;
import Chapter4.validation.Validation;


public class Kohonen extends Training implements Validation {

	/**
	 * 通过应用Kohonen算法训练神经网络，此方法覆盖Training中的方法
	 */
	public NeuralNet train(NeuralNet n) {
		
		int rows = n.getTrainSet().length;
		
		n = this.initNet( n );
		
		ArrayList<Double> listOfDistances = new ArrayList<Double>();
		
		double trainData[][] = n.getTrainSet();
		
		for (int epoch = 0; epoch < n.getMaxEpochs(); epoch++) {
			
			//System.out.println("### EPOCH: "+epoch);
		
			for (int row_i = 0; row_i < rows; row_i++) {
				listOfDistances = calcEuclideanDistance(n, trainData, row_i);
				
				int winnerNeuron = listOfDistances.indexOf(Collections.min(listOfDistances));
				
				n = fixWinnerWeights(n, winnerNeuron, row_i);
				
			}
		
		}
		
		return n;
		
	}
	
	/**
	 * 用 0 初始化来自输入层的神经元列表listOfWeightOut
	 * @param n		未初始化输入层的NeuralNet对象
	 * @return		初始化输入层的NeuralNet对象
	 */
	private NeuralNet initNet(NeuralNet n) {
		ArrayList<Double> listOfWeightOut = new ArrayList<Double>();
		
		for (int i = 0; i < n.getInputLayer().getNumberOfNeuronsInLayer() * n.getOutputLayer().getNumberOfNeuronsInLayer(); i++) {
			listOfWeightOut.add( 0.0 );
		}
		
		n.getInputLayer().getListOfNeurons().get( 0 ).setListOfWeightOut( listOfWeightOut );
		
		return n;
		
	}
	
	/**
	 * 计算训练数据和神经网络权值之间的欧氏距离
	 * @param n		NeuralNet对象
	 * @param data	训练数据
	 * @param row	训练数据的行数
	 * @return		欧氏距离里实际列表
	 */
	private ArrayList<Double> calcEuclideanDistance(NeuralNet n, double[][] data, int row) {
		ArrayList<Double> listOfDistances = new ArrayList<Double>();
		
		int weight_i = 0;
		// 遍历输出层中的所有神经元
		for(int cluster_i = 0; cluster_i < n.getOutputLayer().getNumberOfNeuronsInLayer(); cluster_i++) {
			
			double distance = 0.0;
			
			// 遍历数据集中在相应行的所有输入变量
			for(int input_j = 0; input_j < n.getInputLayer().getNumberOfNeuronsInLayer(); input_j++) {
				
				double weight = n.getInputLayer().getListOfNeurons().get(0).getListOfWeightOut().get(weight_i);
				
				distance = distance + Math.pow(data[row][input_j] - weight, 2.0);
				
				weight_i++;
				
			}
			
			listOfDistances.add(distance);
			
			//System.out.println("distance normal "+cluster_i+": "+distance);
			
		}
		
		return listOfDistances;
		
	}
	
	/**
	 * 调整优胜者神经元的权值（基于欧几里得距离列表）
	 * @param n				NeuralNet对象
	 * @param winnerNeuron	获胜神经元的索引
	 * @param trainSetRow	训练集行数
	 * @return			修改了输入层权值的NeuralNet类
	 */
	private NeuralNet fixWinnerWeights(NeuralNet n, int winnerNeuron, int trainSetRow) {
		int start, last;
		
		start = winnerNeuron * n.getInputLayer().getNumberOfNeuronsInLayer();
		
		if(start < 0) {
			start = 0;
		}
		
		last = start + n.getInputLayer().getNumberOfNeuronsInLayer();
		
		List<Double> listOfOldWeights = new ArrayList<Double>();
		listOfOldWeights = n.getInputLayer().getListOfNeurons().get( 0 ).getListOfWeightOut().subList(start, last);
		
		ArrayList<Double> listOfWeights = new ArrayList<Double>();
		listOfWeights = n.getInputLayer().getListOfNeurons().get( 0 ).getListOfWeightOut();
		
		int col_i = 0;
		for (int j = start; j < last; j++) {
			double trainSetValue = n.getTrainSet()[trainSetRow][col_i];
			double newWeight = listOfOldWeights.get(col_i) + 
					n.getLearningRate() * 
					(trainSetValue - listOfOldWeights.get(col_i));
			
			//System.out.println("newWeight: " + newWeight);
			
			listOfWeights.set(j, newWeight);
			col_i++;
		}
		
		n.getInputLayer().getListOfNeurons().get( 0 ).setListOfWeightOut( listOfWeights );
		
		return n;
		
	}

	/**
	 * 调整优胜者神经元的权值(基于欧几里得距离列表)
	 */
	public void netValidation(NeuralNet n) {
		int rows = n.getValidationSet().length;
		
		ArrayList<Double> listOfDistances = new ArrayList<Double>();
		
		double validationData[][] = n.getValidationSet();
		
		for (int row_i = 0; row_i < rows; row_i++) {
			listOfDistances = calcEuclideanDistance(n, validationData, row_i);
			
			int winnerNeuron = listOfDistances.indexOf(Collections.min(listOfDistances));
			
			System.out.println("### VALIDATION RESULT ###");
			
			switch (winnerNeuron) {
			case 0:
				System.out.println("CLUSTER 1");
				break;
			case 1:
				System.out.println("CLUSTER 2");
				break;
			default:
				throw new IllegalArgumentException("Error! Without neural clustering...");
			}
			
		}
		
	}

}
