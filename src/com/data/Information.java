package com.data;


public enum Information {
	registe_fail("ע��ʧ��!"),
	login_fail("��½ʧ��!"),
	add_fail("��Ӻ���ʧ��!"),
	delete_fail("ɾ������ʧ��!"),
	success("�ɹ�!"),
	null_op("δ���յ����������ص���Ϣ");
	
	String info;
	Information(String info)
	{
		this.info=info;
	}
	public String toString()
	{
		return info;
	}
}
