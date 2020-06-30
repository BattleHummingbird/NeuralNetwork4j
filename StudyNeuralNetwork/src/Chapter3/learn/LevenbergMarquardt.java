package Chapter3.learn;


import java.util.ArrayList;

import Chapter3.NeuralNet;
import Chapter3.Neuron;
import Chapter3.util.IdentityMatrix;
import Chapter3.util.Matrix;

public class LevenbergMarquardt extends Backpropagation {

	private Matrix jacobian = null;	// 在Levenberg-Marquardt算法中使用的jacobian矩阵
    private double damping = 0.1;	// 阻尼系数，类似于学习率
    private Matrix error = null;	
    
    /**
     * 使用Levenberg-Marquardt算法训练神经网络
     */
    public NeuralNet train(NeuralNet n) {
		
		int epoch = 0;
		
		setMse(1.0);
		
		while(getMse() > n.getTargetError()) {
			
			if ( epoch >= n.getMaxEpochs() ) break;
			
			int rows = n.getTrainSet().length;
			double sumErrors = 0.0;
			
			for (int rows_i = 0; rows_i < rows; rows_i++) {
				
				n = forward(n, rows_i);
				
				buildJacobianMatrix(n, rows_i);
				
				sumErrors = sumErrors + n.getErrorMean();
				
			}
                        
            setMse( sumErrors / rows );
                                                
            n=updateWeights(n);
			
			System.out.println( getMse() );
			
			epoch++;
			
		}
		
		System.out.println("Number of epochs: "+epoch);
		
		return n;
		
	}

    /**
     * 通过训练数据集的相应行，为神经网络的每个权值和偏置计算梯度并保存到Jacobian矩阵相应行里
     * @param n		未训练的神经网络
     * @param row 	第i个数据点
     */
	private void buildJacobianMatrix(NeuralNet n, int row){
		ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
        outputLayer = n.getOutputLayer().getListOfNeurons();
		
        ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
        hiddenLayer = n.getListOfHiddenLayer().get(0).getListOfNeurons();
            
        NeuralNet nb = backpropagation(n,row);
            
        int numberOfInputs = n.getInputLayer().getNumberOfNeuronsInLayer();
        int numberOfHiddenNeurons = n.getHiddenLayer().getNumberOfNeuronsInLayer();
        int numberOfOutputs = n.getOutputLayer().getNumberOfNeuronsInLayer();
        
        if(jacobian == null){
            jacobian = new Matrix(n.getTrainSet().length,
                    (numberOfInputs)*(numberOfHiddenNeurons-1)+
                            (numberOfHiddenNeurons)*(numberOfOutputs));
        }
            
        int i=0;
        //Hidden Layer
        for (Neuron neuron : hiddenLayer){
            
            ArrayList<Double> hiddenLayerInputWeights = new ArrayList<Double>();
            hiddenLayerInputWeights = neuron.getListOfWeightIn();
		
            if(hiddenLayerInputWeights.size() > 0) { //exclude bias
                for (int j = 0; j < n.getInputLayer().getNumberOfNeuronsInLayer(); j++) {
                		jacobian.setValue(row,((i-1)*(numberOfInputs))+(j),
                            (neuron.getSensibility() * n.getTrainSet()[row][j])/n.getErrorMean()); 
                }                
            }else{
                //jacobian.setValue(row,i*(numberOfInputs),1.0); 
            }
            //bias will have no effect
            
            i++;
        }
        
        if(error == null){
            error = new Matrix(n.getTrainSet().length,1);
        }            
        
        i=0;

        //Output Layer
        for (Neuron output : outputLayer) {
            int j=0;
            for (Neuron neuron : hiddenLayer) {
                jacobian.setValue(row,
                        (numberOfInputs)*(numberOfHiddenNeurons-1)+
                                (i*(numberOfHiddenNeurons))+j,
                        (output.getSensibility() * neuron.getOutputValue())/n.getErrorMean());
                j++;
            }
            //bias will have no effect
            //jacobian.setValue(row,(numberOfInputs)*(numberOfHiddenNeurons-1)+
            //                    (i*(numberOfHiddenNeurons))+numberOfHiddenNeurons,1.0); 
            i++;
        }
        
        error.setValue(row,0,n.getErrorMean());
    }
        
       
    private NeuralNet updateWeights(NeuralNet n){
        // delta = inv(J`J + damping I ) * J` error
        Matrix term1 = jacobian.transpose().multiply(jacobian)
                .add(new IdentityMatrix(jacobian.getNumberOfColumns())
                        .multiply(damping));
        Matrix term2 = jacobian.transpose().multiply(error);
        Matrix delta = term1.inverse().multiply(term2);
        
        ArrayList<Neuron> outputLayer = new ArrayList<Neuron>();
        outputLayer = n.getOutputLayer().getListOfNeurons();
	
        ArrayList<Neuron> hiddenLayer = new ArrayList<Neuron>();
        hiddenLayer = n.getListOfHiddenLayer().get(0).getListOfNeurons();
        
        int numberOfInputs = n.getInputLayer().getNumberOfNeuronsInLayer();
        int numberOfHiddenNeurons = n.getHiddenLayer().getNumberOfNeuronsInLayer();
        int numberOfOutputs = n.getOutputLayer().getNumberOfNeuronsInLayer();
        
        int i=0;
        for(Neuron hidden:hiddenLayer){
            ArrayList<Double> hiddenLayerInputWeights = new ArrayList<Double>();
	hiddenLayerInputWeights = hidden.getListOfWeightIn();
		
	if(hiddenLayerInputWeights.size() > 0) { //exclude bias
			
                    double newWeight = 0.0;
                    for (int j = 0; j < n.getInputLayer().getNumberOfNeuronsInLayer(); j++) {
					
                        newWeight = hiddenLayerInputWeights.get(i) + delta.getValue(((i)*(numberOfInputs)+(j)) ,0) ;
			hidden.getListOfWeightIn().set(i, newWeight);
				
                    }
                    i++;		
		}  
                
            }
            
            i =0;
            for(Neuron output:outputLayer){
                int j=0;
                double newWeight =0.0;
                for (Neuron neuron : hiddenLayer) {
                    newWeight = neuron.getListOfWeightOut().get(i) + 
                            delta.getValue((numberOfInputs)*(numberOfHiddenNeurons-1)+
                                    (i*(numberOfHiddenNeurons))+j , 0) ;
                    neuron.getListOfWeightOut().set(i, newWeight);
                    j++;
                }
                i++;
            }
           
            
            return n;
        } 

    
}
