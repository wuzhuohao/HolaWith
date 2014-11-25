package com.holawith.notebook;

import java.io.Serializable;

/** �绰�� */
public class NoteBookItem implements Serializable {
	
	/**������ĸ*/
	public String index;

	/** ���µ�ֵ */
	public String update=null;
	/** ���� */
	public String name;
	/** �ƺ� */
	public String call;
	/** �绰���� */
	public String number="null";
	/** �ֻ���� */
	public String mobile="null";
	/** mail */
	public String mail;

	@Override
	public String toString() {
		return "NoteBookItem [update=" + update + ", name=" + name + ", call="
				+ call + ", number=" + number + ", mobile=" + mobile
				+ ", mail=" + mail + "]";
	}
}
