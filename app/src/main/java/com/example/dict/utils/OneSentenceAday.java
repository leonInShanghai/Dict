package com.example.dict.utils;

import android.util.Log;

import java.util.Random;

/**
 * Created by 公众号：IT波 on 2021/8/22 Copyright © Leon. All rights reserved.
 * Functions: 每日语句工具类
 */
public class OneSentenceAday {

    private String[] mStrings = {"I don't mind your past, what I mind is : Your past has not passed.",
    "Summer is over, Autumn will start all over again, if you have nothing to do, Then stay with me for winter.",
    "If you talk to someone. And the don't respond. Please remember: No respone.   Is also respone. And... ，it's " +
            "a powerful one. ",
    "In the coming days. If you feel like you are on verge of giving up. Tell youself. Hang in there. Step forward a " +
            "little. Then good things if you stay consciously positive will happen naturally. ",
    "When the heart is tired change an angle to view the world. When hesitated change your way to select. " +
            "When wronged change your mind to relax youself.",
    "If you can't fly then run. If you can't run then walk. If you can't walk then crawl.", "Life is too short to wake " +
            "up the morning with regrets, So love the people who treat you right, and forget about the ones who don't, " +
            "And believe: That everything happens for a reson.", "Four things you can never recover in life, a word after" +
            " it is said, an opportunity after it is missed, time after it is gone, and trust after it is lost. ",
     "Before you talk, listen. Before you react , wait. Before you get angry, forgive. Before you quit, try.",
            "If one day you are more and more silent, and don't want talk it's not cowardice, it's not compmise. It " +
                    "means you're taking a lot of you saw a lot of people. You are more and more mature. ",
    " The most beautiful words in the world are not I love you, But when you need me the most I will tell you I'm always" +
            " here for you.", "Spare more time to shape youself, and less to think about other.", "If you are not happy," +
            " change your circle, change your mindset, change your look, get in shape, And be financially independent.",
    "Never reply whe you are angry.  Never make a promise when you are happy. Never make a decision when you are sad.",
    "Don't forget to do four things everyday. Dream big. Plan well. Work hard.  And smile always."};


    // 静态内部类单例 《Effective Java》上所推荐的。
    private static class SingletonHolder {
        private static final OneSentenceAday INSTANCE = new OneSentenceAday();
    }

    private OneSentenceAday() {
        Log.d("OneSentenceAday", " 被实例化了 此类是单例只能打印一次!");
    }

    public static final OneSentenceAday getInstance() {
        return OneSentenceAday.SingletonHolder.INSTANCE;
    }

    public String getOneSentence() {
        Random random = new Random();
        int num = random.nextInt(mStrings.length - 1);
        return mStrings[num];
    }

}
