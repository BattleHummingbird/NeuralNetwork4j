package test00;

import java.util.ArrayList;

public abstract class Layer {
	
	private ArrayList<Neuron> listOfNeurons; //һ��ArrayList������Ԫ������ΪNeuron��
	private int numberOfNeuronsInLayer;		 //���� ���� �����Ԫ����
	
	/***
	 * ���ز����Ԫ����
	 * @param None
	 * @return һ��ArrayList������Ԫ������ΪNeuron��
	 */
	public ArrayList<Neuron> getListOfNeurons() {
		return listOfNeurons;
	}
	
	/***
	 * ��һ��Neuron��������listOfNeurons�ĺ���
	 * @param �����洢��Neuron����
	 * @return None
	 */
	public void setListOfNeurons(ArrayList<Neuron> listOfNeurons) {
		this.listOfNeurons = listOfNeurons;
	}
	
	/**
	 * ���ز����Ԫ������
	 * @param None
	 * @return �����Ԫ����
	 */
	public int getNumberOfNeuronsInLayer() {
		return numberOfNeuronsInLayer;
	}

	/**
	 * ���ò����Ԫ������
	 * @param �����Ԫ����
	 * @return None
	 */
	public void setNumberOfNeuronsInLayer(int numberOfNeuronsInLayer) {
		this.numberOfNeuronsInLayer = numberOfNeuronsInLayer;
	}
	
	
}
