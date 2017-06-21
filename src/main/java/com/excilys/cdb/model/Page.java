package com.excilys.cdb.model;

import java.util.List;

public class Page<T> {
	
	private List<T> objectsList;
	
	private int size;
	
	private int number;

	public List<T> getObjectsList() {
		return objectsList;
	}

	public void setObjectsList(List<T> objectsList) {
		this.objectsList = objectsList;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public Page() {
		
	}

	public Page(List<T> objectsList, int size, int number) {
		this.objectsList = objectsList;
		this.size = size;
		this.number = number;
	}
	
	public String toString() {
		String res = "";
		
		for(T objet : objectsList) {
			res+=objet.toString()+"\n";
		}
		return res;
	}
	
	
}