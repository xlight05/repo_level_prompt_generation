package br.com.dyad.infrastructure.navigation.tests;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;

import br.com.dyad.infrastructure.navigation.GenericEntityBeanWindow;

public class TesteBigIntegerWindow extends GenericEntityBeanWindow {
	
	public TesteBigIntegerWindow(HttpSession httpSession) {
		super(httpSession);
	}

	@Override
	public void defineWindow() throws Exception {
		setBeanName(TesteBigInteger.class.getName());
		
		ArrayList<TesteBigInteger> temp = new ArrayList<TesteBigInteger>();
		
		for (long i = 0L; i < 5L; i++) {
			TesteBigInteger t = new TesteBigInteger();
			t.setNome("Nome " + i);
			t.setValorInteiro(new BigInteger((124799 + i) + ""));
			t.setValorReal(new BigDecimal(i + ".55"));
			
 			temp.add(t);
		}				
		
		this.dataList.setList(temp);
		
		super.defineWindow();
	}
}