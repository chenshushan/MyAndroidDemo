package com.example.chen.myapplication.app.service;

import com.example.chen.myapplication.app.bean.Shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ShopService {

	private static Random random = new Random();
	private static int shopSeq = 1;


	public List<Shop> getShops(){
	   	List<Shop> list = new ArrayList();
		for (int i = 0; i < 10; i++) {
			Shop shop = new Shop();
			shop.setId(shopSeq++);
			shop.setName("");
			shop.setHasSell(random.nextInt(3000));
			shop.setShoudan(random.nextInt(9) + 1);
			shop.setMinPrice(Math.random()*30 + 5);
			shop.setFull(shop.getMinPrice() + random.nextInt(20) + 10);
			shop.setJian(random.nextInt(10) + 5);
			shop.setPeisong(random.nextInt(4) + 1);


			shop.setDistance(String.format("%.2f", Math.random()*30));
			shop.setPicUrl("");
			list.add(shop);
		}
		return list;
	}



}
