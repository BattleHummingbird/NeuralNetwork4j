package Chapter4.validation;

import Chapter4.NeuralNet;

public interface Validation {

	/**
	 *  执行神经网络验证，在PC屏幕上打印一些结果
	 * @param n
	 */
	public void netValidation(NeuralNet n);
	
}
