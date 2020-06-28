package test00;

import java.util.ArrayList;
import java.util.Random;

public class Neuron {
	
	private ArrayList<Double> listOfWeightIn;    //һ��ʵ��ArrayList��������������Ȩֵ�ļ���
	private ArrayList<Double> listOfWeightOut;	 //һ��ʵ��ArrayList�������������Ȩֵ�ļ���
	
	/**
	 * ��α���ʵ����ʼ�� listOfWeightIn �� listOfWeightOut 
	 * @param None
	 * @return һ��α���ʵ��
	 */
	public double initNeuron() {
		//����һ��α���ʵ��
		Random rad=new Random(); 
		Double initD = rad.nextDouble();
		
		
		
		listOfWeightIn.add(initD);
		listOfWeightOut.add(initD);
		return initD;
	}
	
	/**
	 * ��һ��ʵ���������� listOfWeightIn �ĺ���
	 * @param �����洢��������ʵ���б�
	 * @return None
	 */
	public void setListOfWeightIn(ArrayList<Double> listOfWeightIn) {
		this.listOfWeightIn = listOfWeightIn;
	}
	
	/**
	 * ��һ��ʵ���������� listOfWeightOut �ĺ���
	 * @param �����洢��������ʵ���б�
	 * @return None
	 */
	public void setListOfWeightOut(ArrayList<Double> listOfWeightOut) {
		this.listOfWeightOut = listOfWeightOut;
	}
	
	/**
	 * ������Ԫ���ϵ�����Ȩֵ
	 * @param None
	 * @return �洢�� listOfWeightIn �������ʵ������
	 */
	public ArrayList<Double> getListOfWeightIn() {
		return listOfWeightIn;
	}
	
	/**
	 * ������Ԫ���ϵ����Ȩֵ
	 * @param None
	 * @return �洢�� listOfWeightOut �������ʵ������
	 */
	public ArrayList<Double> getListOfWeightOut() {
		return listOfWeightOut;
	}
}
