package com.pingfangx.demo.androidx.activity.program.base;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static com.pingfangx.demo.androidx.activity.program.base.FloatArithmetic.getFormattedBinaryString;

/**
 * 浮点运算测试
 *
 * @author pingfangx
 * @date 2018/8/1
 */
public class FloatArithmeticTest {
    @Test
    public void test() {
        print(getFormattedBinaryString(0, "零"));
        print(getFormattedBinaryString(-0, "负零"));
        print(getFormattedBinaryString(1, "1"));
        print(getFormattedBinaryString(-1, "-1"));
        print(getFormattedBinaryString(Float.NEGATIVE_INFINITY, "负无穷"));
        print(getFormattedBinaryString(Float.POSITIVE_INFINITY, "正无穷"));
        print(getFormattedBinaryString(Float.NaN, "NaN"));
        print(getFormattedBinaryString(Float.MIN_VALUE, "最小值"));
        print(getFormattedBinaryString(Float.MAX_VALUE, "最大值"));
        print(getFormattedBinaryString(Float.MIN_NORMAL, "最小规约数"));
        print(getFormattedBinaryString(0.5, "1*2^-1"));
        print(getFormattedBinaryString(0.25, "1*2^-2"));
        print(getFormattedBinaryString(2, "1*2^1"));
        print(FloatArithmetic.getFormattedBinaryString(2.5, "1.01*2^1"));
        print(FloatArithmetic.getFormattedBinaryString(0.1));
        print(FloatArithmetic.getFormattedBinaryString(6.6));
        print(FloatArithmetic.getFormattedBinaryString(8.8));
        print(new BigDecimal(6.6));
        print(new BigDecimal(8.8));
    }

    public void print(Object o) {
        if (o.getClass().isArray()) {
            print(Arrays.toString((Object[]) o));
        } else {
            System.out.println(o.toString());
        }
    }

    @Test
    public void floatToBinaryString() {
        print(FloatArithmetic.floatToBinaryString(6.6F));
    }

    @Test
    public void movePoint() {
        print(FloatArithmetic.getScientificNotationRepresentation("1"));
        print(FloatArithmetic.getScientificNotationRepresentation("0.1"));
        print(FloatArithmetic.getScientificNotationRepresentation("0.00001"));
        print(FloatArithmetic.getScientificNotationRepresentation("1.1"));
        print(FloatArithmetic.getScientificNotationRepresentation("10.1"));
    }

    @Test
    public void getFloatRepresentationComment() {
        print(FloatArithmetic.getRepresentationComment(6.6F));
        print(FloatArithmetic.getRepresentationComment(8.8F));
        print(FloatArithmetic.getRepresentationComment(1.1));
        print(FloatArithmetic.getRepresentationComment(0.1));
        print(FloatArithmetic.getRepresentationComment(1.1 + 0.1));
    }

    @Test
    public void binaryStringToDecimalFloat() {
        print(FloatArithmetic.binaryStringToDecimalFloatString("1.1"));
        print(FloatArithmetic.binaryStringToDecimalFloatString("10.01"));
        CharSequence binarySequence = FloatArithmetic.floatToBinaryString(8.8F);
        print(binarySequence);
        print(FloatArithmetic.binaryStringToDecimalFloatString(binarySequence, true));
    }

    @Test
    public void trimFraction() {
        print(FloatArithmetic.trimFraction("1.000", 2));
        print(FloatArithmetic.trimFraction("1.001", 2));
        print(FloatArithmetic.trimFraction("1.011", 2));
        print(FloatArithmetic.trimFraction("1.111", 2));
        print(FloatArithmetic.trimFraction("11.111", 2));
    }

    @Test
    public void scientificNotationRepresentationToNormal() {
        print(FloatArithmetic.scientificNotationRepresentationToNormal("1.1", 1));
        print(FloatArithmetic.scientificNotationRepresentationToNormal("1.11", 1));
        print(FloatArithmetic.scientificNotationRepresentationToNormal("1.11", 0));
        print(FloatArithmetic.scientificNotationRepresentationToNormal("1.11", -1));
        print(FloatArithmetic.scientificNotationRepresentationToNormal("1.11", -2));
    }

    @Test
    public void testAdd() {
        print(FloatArithmetic.testAdd(1.1, 0.1));
    }

    @Test
    public void binaryAdd() {
        print(FloatArithmetic.binaryAdd("1.1", "0.0"));
        print(FloatArithmetic.binaryAdd("1.01", "0.1"));
        print(FloatArithmetic.binaryAdd("1.11", "1.11"));
    }
}
