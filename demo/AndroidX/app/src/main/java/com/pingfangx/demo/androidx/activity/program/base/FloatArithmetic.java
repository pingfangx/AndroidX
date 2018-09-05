package com.pingfangx.demo.androidx.activity.program.base;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * 浮点运算测试
 *
 * @author pingfangx
 * @date 2018/8/1
 */
public class FloatArithmetic {

    public static CharSequence getFormattedBinaryString(Number number) {
        return getFormattedBinaryString(number, "");
    }

    public static CharSequence getFormattedBinaryString(Number number, CharSequence title) {
        return getFormattedBinaryString(number, title, "");
    }

    public static CharSequence getFormattedBinaryString(Number number, CharSequence title, CharSequence comment) {
        Float floatValue = number.floatValue();
        StringBuilder stringBuilder = new StringBuilder();
        //格式化展示的数字
        stringBuilder.append(getFormattedBitsString(floatValue));
        stringBuilder.append("|");
        //数值
        stringBuilder.append(leftJust(floatValue.toString(), 10, ' '));
        stringBuilder.append("|");
        //二进制表示
        CharSequence binaryString = floatToBinaryString(floatValue);
        stringBuilder.append(binaryString);
        stringBuilder.append("|");
        //移动小数点
        stringBuilder.append(getScientificNotationRepresentation(binaryString)[0]);
        stringBuilder.append("|");
        stringBuilder.append(getScientificNotationRepresentation(binaryString)[0]);
        stringBuilder.append("|");
        //标题
        stringBuilder.append(title);
        stringBuilder.append("|");
        //注释
        stringBuilder.append(comment);
        return stringBuilder;
    }

    /**
     * 获取科学计数法表示
     */
    public static Object[] getScientificNotationRepresentation(CharSequence numberSequence) {
        int position = numberSequence.toString().indexOf(".");
        if (position < 0) {
            return new CharSequence[]{"",""};
        }
        StringBuilder stringBuilder = new StringBuilder();
        //小数点前
        stringBuilder.append(numberSequence.subSequence(0, position));
        //小数点后
        stringBuilder.append(numberSequence.subSequence(position + 1, numberSequence.length()));
        //查找新的小数点位置
        int newPosition = 0;
        for (int i = 0; i < stringBuilder.length(); i++) {
            if (stringBuilder.charAt(i) == '1') {
                newPosition = i + 1;
                break;
            }
        }
        if (newPosition == 0) {
            return new CharSequence[]{"",""};
        }
        //插入小数点
        stringBuilder.insert(newPosition, ".");
        if (newPosition == stringBuilder.length() - 1) {
            //补0
            stringBuilder.append('0');
        }
        //删除前面无效的 0
        stringBuilder.delete(0, newPosition - 1);
        //删除第一位
//        stringBuilder.deleteCharAt(0);
        //前面处理完成
        return new Object[]{stringBuilder, position - newPosition};
    }

    /**
     * 科学计数法转为常规表示,即移动小数点
     */
    public static CharSequence scientificNotationRepresentationToNormal(CharSequence source, int exponent) {
        StringBuilder stringBuilder = new StringBuilder(source);
        //删除小数
        stringBuilder.deleteCharAt(1);
        //添加小数点
        if (exponent >= 0) {
            stringBuilder.insert(1 + exponent, '.');
        } else {
            //小于0
            for (int i = -1; i > exponent; i--) {
                stringBuilder.insert(0, '0');
            }
            //最前面插入
            stringBuilder.insert(0, '.');
            stringBuilder.insert(0, '0');
        }
        return stringBuilder;
    }

    public static CharSequence trimScientificNotationRepresentation(CharSequence source, int maxLength) {
        int spacePosition = -1;
        for (int i = 0; i < source.length(); i++) {
            if (source.charAt(i) == ' ') {
                spacePosition = i;
                break;
            }
        }
        if (spacePosition == -1) {
            return trimFraction(source, maxLength);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            CharSequence trimFraction = trimFraction(source.subSequence(0, spacePosition), maxLength);
            stringBuilder.append(trimFraction);
            stringBuilder.append(source.subSequence(spacePosition, source.length()));
            return stringBuilder;
        }
    }

    /**
     * 截取小效小数位数
     */
    public static CharSequence trimFraction(CharSequence source, int maxFractionLength) {
        int position = -1;
        for (int i = 0; i < source.length(); i++) {
            if (source.charAt(i) == '.') {
                position = i;
                break;
            }
        }
        if (position == -1) {
            return source;
        }
        //减掉整数与小数点
        if (source.length() - (position + 1) <= maxFractionLength) {
            return source;
        }
        //截取
        StringBuilder stringBuilder = new StringBuilder(source.subSequence(0, maxFractionLength + position + 1));
        //最后一位
        if (source.charAt(maxFractionLength + 2) == '1') {
            //需要进位
            int i;
            for (i = stringBuilder.length() - 1; i >= 0; i--) {
                char c = source.charAt(i);
                if (c == '0') {
                    //设为1
                    stringBuilder.setCharAt(i, '1');
                    break;
                } else if (c == '1') {
                    //设为0，继续进位
                    stringBuilder.setCharAt(i, '0');
                }
                //小数点，继续
            }
            if (i < 0) {
                //说明到头了
                stringBuilder.insert(0, '1');
            }
        }
        return stringBuilder;
    }

    /**
     * 二进制字符串转回浮点
     */
    public static CharSequence binaryStringToDecimalFloatString(CharSequence binarySequence) {
        return binaryStringToDecimalFloatString(binarySequence, false);
    }

    public static CharSequence binaryStringToDecimalFloatString(CharSequence binarySequence, boolean print) {
        //查找小数点
        int position = -1;
        for (int i = 0; i < binarySequence.length(); i++) {
            if (binarySequence.charAt(i) == '.') {
                position = i;
                break;
            }
        }
        if (position < 0) {
            return null;
        }
        //直接转为整数
        Integer integer = Integer.valueOf(binarySequence.subSequence(0, position).toString(), 2);
        //小数部分
        CharSequence fractionSequence = binarySequence.subSequence(position + 1, binarySequence.length());
        BigDecimal bigDecimal = new BigDecimal(integer);
        for (int i = 0; i < fractionSequence.length(); i++) {
            char c = fractionSequence.charAt(i);
            if (c == '1') {
                //次方累加
                double pow = Math.pow(2, 0 - i - 1);
                bigDecimal = bigDecimal.add(new BigDecimal(pow));
                if (print) {
                    System.out.println(String.format(Locale.getDefault(), "%s,%s", new BigDecimal(pow).toPlainString(), bigDecimal));
                }
            }
        }
        return bigDecimal.toString();
    }

    /**
     * 浮点转为二进制字符串
     */
    public static CharSequence floatToBinaryString(Float number) {
        return numberToBinaryString(number, 32);
    }

    public static CharSequence doubleToBinaryString(Double number) {
        return numberToBinaryString(number, 64);
    }

    /**
     * 最大位数并不是实际展示的位数，还需要移动
     */
    public static CharSequence numberToBinaryString(Number number, int maxLength) {
        Double doubleValue = number.doubleValue() > 0 ? number.doubleValue() : 0 - number.doubleValue();
        StringBuilder sb = new StringBuilder();
        if (number.doubleValue() < 0) {
            sb.append('-');
        }
        Number[] numbers = splitDouble(doubleValue);
        if (numbers == null) {
            return "";
        }
        sb.append(Integer.toBinaryString(numbers[0].intValue()));
        sb.append('.');
        sb.append(fractionToBinaryString(numbers[1].doubleValue(), maxLength));
        return sb;
    }

    /**
     * 小数部分转为二进制表示
     */
    public static CharSequence fractionToBinaryString(Double fraction, int maxLength) {
        StringBuilder sb = new StringBuilder();
        while (fraction != 0 && sb.length() <= maxLength) {
            fraction *= 2;
            Number[] numbers = splitDouble(fraction);
            if (numbers == null) {
                return null;
            }
            //添加整数
            sb.append(numbers[0].intValue());
            //减去整数
            fraction = numbers[1].doubleValue();
        }
        if (sb.length() == 0) {
            //如果没有小数,也手动添加一个 0
            sb.append('0');
        }
        return sb;
    }

    /**
     * 将浮点转为整数和小数
     * 如果直接减去整数部分,又会出现精度问题,索性直接用字符串处理
     */
    public static Number[] splitDouble(Double number) {
        String s = number.toString();
        int position = s.indexOf(".");
        if (position >= 0) {
            return new Number[]{Integer.valueOf(s.substring(0, position)), Double.valueOf(s.substring(position))};
        } else {
            return null;
        }
    }

    /**
     * 格式化数字
     */
    public static CharSequence getFormattedBitsString(Float number) {
        //二进制
        CharSequence source = Integer.toBinaryString(Float.floatToIntBits(number));
        //补齐 32 位
        source = leftJust(source, 32, '0');
        //符号
        CharSequence sign = source.subSequence(0, 1);
        //指数
        CharSequence exponent = source.subSequence(1, 9);
        //有效数字,尾数,系数
        CharSequence significand = source.subSequence(9, source.length());
        int biasExponent = Integer.valueOf(exponent.toString(), 2);
        int realExponent = biasExponent - 127;
        StringBuilder sb = new StringBuilder();
        sb.append(sign);
        sb.append('|');
        sb.append(leftJust(Integer.toString(realExponent), 5, ' '));
        sb.append('|');
        sb.append(leftJust(Integer.toString(biasExponent), 5, ' '));
        sb.append('|');
        sb.append(addSeparator(exponent, 4, ' '));
        sb.append('|');
        sb.append(addSeparator(significand, 4, ' '));
        return sb;
    }

    public static StringBuilder getRepresentationComment(Number number) {
        BitsRange bitsRange;
        BigDecimal bigDecimal;
        if (number instanceof Float) {
            Float floatNumber = (Float) number;
            bitsRange = BitsRange.fromNumber(floatNumber);
            bigDecimal = new BigDecimal(floatNumber);
        } else if (number instanceof Double) {
            Double doubleNumber = (Double) number;
            bitsRange = BitsRange.fromNumber(doubleNumber);
            bigDecimal = new BigDecimal(doubleNumber);
        } else {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(bitsRange.getRepresentationComment());
        stringBuilder.append("\nBigDecimal 表示\t");
        stringBuilder.append(bigDecimal.toPlainString());
        stringBuilder.append("\n先转为 String，再用 BigDecimal 表示\t");
        stringBuilder.append(new BigDecimal(number.toString()).toPlainString());
        stringBuilder.append('\n');
        return stringBuilder;
    }

    static class BitsRange {
        private Number number;
        /**
         * 全部位数
         */
        private int allBits;
        /**
         * 符号位数
         */
        private int signBits;
        /**
         * 指数位数
         */
        private int exponentBits;
        /**
         * 尾数位数
         */
        private int significandBits;
        /**
         * 指数偏移
         */
        private int exponentBias;

        private CharSequence binaryString;
        private CharSequence justBinaryString;
        private CharSequence signString;
        private CharSequence exponentString;
        private CharSequence significandString;

        private int exponentWithBias;
        private int exponentReal;
        //手动求的
        private CharSequence manualBinaryString;
        private CharSequence manualTrimBinaryString;
        private CharSequence manualScientificString;
        private CharSequence manualTrimScientificString;
        private int manualScientificExponent;

        public BitsRange(int allBits, int signBits, int exponentBits, int significandBits) {
            this.signBits = signBits;
            this.exponentBits = exponentBits;
            this.significandBits = significandBits;
            this.allBits = allBits;
            this.exponentBias = (int) (Math.pow(2, this.exponentBits - 1) - 1);
        }

        public void initSystemBinaryString(Number number, CharSequence binaryString) {
            this.number = number;
            //二进制表示
            this.binaryString = binaryString;
            //补齐0
            this.justBinaryString = leftJust(this.binaryString, this.allBits, '0');
            this.signString = justBinaryString.subSequence(0, this.signBits);
            this.exponentString = justBinaryString.subSequence(this.signBits, this.signBits + this.exponentBits);
            this.significandString = justBinaryString.subSequence(this.signBits + this.exponentBits, justBinaryString.length());
            this.exponentWithBias = Integer.valueOf(this.exponentString.toString(), 2);
            this.exponentReal = this.exponentWithBias - this.exponentBias;

            //手动求的
            manualBinaryString = numberToBinaryString(number, this.allBits);
            initManualScientificString(manualBinaryString);
        }

        /**
         * 获取科学计数法表示
         */
        private void initManualScientificString(CharSequence numberSequence) {
            int position = numberSequence.toString().indexOf(".");
            if (position < 0) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            //小数点前
            stringBuilder.append(numberSequence.subSequence(0, position));
            //小数点后
            stringBuilder.append(numberSequence.subSequence(position + 1, numberSequence.length()));
            //查找新的小数点位置
            int newPosition = 0;
            for (int i = 0; i < stringBuilder.length(); i++) {
                if (stringBuilder.charAt(i) == '1') {
                    newPosition = i + 1;
                    break;
                }
            }
            if (newPosition == 0) {
                return;
            }
            //插入小数点
            stringBuilder.insert(newPosition, ".");
            if (newPosition == stringBuilder.length() - 1) {
                //补0
                stringBuilder.append('0');
            }
            //删除前面无效的 0
            stringBuilder.delete(0, newPosition - 1);
            //删除第一位
//        stringBuilder.deleteCharAt(0);
            //前面处理完成
            manualScientificString = stringBuilder;
            manualTrimScientificString = trimScientificNotationRepresentation(manualScientificString, significandBits);
            manualScientificExponent = position - newPosition;
            manualTrimBinaryString = scientificNotationRepresentationToNormal(manualTrimScientificString, manualScientificExponent);
        }

        public static BitsRange fromNumber(Float number) {
            BitsRange bitsRange = new BitsRange(32, 1, 8, 23);
            bitsRange.initSystemBinaryString(number, Integer.toBinaryString(Float.floatToIntBits(number)));
            return bitsRange;
        }

        public static BitsRange fromNumber(Double number) {
            BitsRange bitsRange = new BitsRange(64, 1, 11, 52);
            bitsRange.initSystemBinaryString(number, Long.toBinaryString(Double.doubleToLongBits(number)));
            return bitsRange;
        }

        /**
         * 格式化数字
         */
        public CharSequence getFormattedBitsStringWithComment() {
            StringBuilder sb = new StringBuilder();
            sb.append(signString);
            sb.append('|');
            sb.append(addSeparator(exponentString, 4, ' '));
            sb.append('|');
            sb.append(addSeparator(significandString, 4, ' '));
            sb.append('|');
            sb.append("指数（带偏移）");
            sb.append(leftJust(Integer.toString(exponentWithBias), 5, ' '));
            sb.append('|');
            sb.append("实际表示指数");
            sb.append(leftJust(Integer.toString(exponentReal), 5, ' '));
            return sb;
        }

        /**
         * 获取浮点数表示的解释
         */
        public CharSequence getRepresentationComment() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("数值为\t" + number);
            stringBuilder.append("\n转为二进制表示\t" + manualBinaryString);
            stringBuilder.append("\n转为科学计数法表示\t");
            stringBuilder.append(manualScientificString);
            stringBuilder.append(' ').append('*').append('2').append('^').append(manualScientificExponent);
            stringBuilder.append("\n截取 ").append(significandBits).append(" 位\t");
            stringBuilder.append(addSpaceSeparator(manualTrimScientificString));
            stringBuilder.append(' ').append('*').append('2').append('^').append(manualScientificExponent);
            stringBuilder.append("\n实际系统存储\t");
            stringBuilder.append(getFormattedBitsStringWithComment());
            stringBuilder.append("\n转回二进制\t");
            stringBuilder.append(manualTrimBinaryString);
            stringBuilder.append("\n再转回十进制\t");
            stringBuilder.append(binaryStringToDecimalFloatString(manualTrimBinaryString));
            return stringBuilder;
        }

    }

    public static CharSequence testAdd(Double n1, Double n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getRepresentationComment(n1));
        stringBuilder.append('\n');
        stringBuilder.append(getRepresentationComment(n2));
        stringBuilder.append('\n');
        stringBuilder.append(getRepresentationComment(n1 + n2));
        stringBuilder.append('\n');
        BitsRange bitsRange1 = BitsRange.fromNumber(n1);
        BitsRange bitsRange2 = BitsRange.fromNumber(n2);
        CharSequence addResult = binaryAdd(bitsRange1.manualTrimBinaryString, bitsRange2.manualTrimBinaryString);
        stringBuilder.append("二进制相加");
        stringBuilder.append('\n');
        stringBuilder.append('a').append('=').append(bitsRange1.manualTrimBinaryString);
        stringBuilder.append('\n');
        stringBuilder.append('b').append('=').append(bitsRange2.manualTrimBinaryString);
        stringBuilder.append('\n');
        stringBuilder.append('c').append('=').append(addResult);
        stringBuilder.append('\n');
        stringBuilder.append("科学计数法表示\n");
        Object[] scientificString = getScientificNotationRepresentation(addResult);
        stringBuilder.append(scientificString[0]);
        stringBuilder.append(' ').append('*').append(' ').append('2').append('^').append(scientificString[1]);
        stringBuilder.append('\n');
        stringBuilder.append("截取 ").append(bitsRange1.significandBits).append(" 位\n");
        CharSequence trimFraction = trimFraction((CharSequence) scientificString[0], bitsRange1.significandBits);
        CharSequence trimScientificString = scientificNotationRepresentationToNormal(trimFraction, (int) scientificString[1]);
        stringBuilder.append(trimScientificString);
        stringBuilder.append(' ').append('*').append(' ').append('2').append('^').append(scientificString[1]);
        stringBuilder.append("\n转为十进制\n").append(binaryStringToDecimalFloatString(trimScientificString));
        return stringBuilder;
    }

    /**
     * 二进制相加
     * 两个参数必须为 1.** 的形式
     */
    public static CharSequence binaryAdd(CharSequence charSequence1, CharSequence charSequence2) {
        int length1 = charSequence1.length();
        int length2 = charSequence2.length();
        int length = length1 > length2 ? length1 : length2;
        StringBuilder result = new StringBuilder(length);
        result.setLength(length);
        int preAdd = 0;
        for (int i = length - 1; i >= 0; i--) {
            if (i == 1) {
                //小数点
                continue;
            }
            if (i >= length1) {
                result.setCharAt(i, charSequence2.charAt(i));
            } else if (i >= length2) {
                result.setCharAt(i, charSequence1.charAt(i));
            } else {
                //相加
                int add = (charSequence1.charAt(i) - '0') + ((int) charSequence2.charAt(i) - '0');
                //加上进位值
                add += preAdd;
                if (add > 1) {
                    //进位
                    result.setCharAt(i, (char) (add - 2 + '0'));
                    preAdd = 1;
                } else {
                    //不进位
                    result.setCharAt(i, (char) (add + '0'));
                    preAdd = 0;
                }
            }
        }
        //补上小数点
        result.setCharAt(1, '.');
        if (preAdd > 0) {
            //需要再进位
            result.insert(0, '1');
        }
        return result;
    }

    /**
     * 添加分隔符
     */
    public static CharSequence addSpaceSeparator(CharSequence source) {
        return addSeparator(source, 4, ' ');
    }

    public static CharSequence addSeparator(CharSequence source, int width, char separator) {
        StringBuilder sb = new StringBuilder(source);
        int length = source.length();
        //最开头不用添加
        for (int i = length - 1; i > 0; i--) {
            if (source.charAt(i) == '.') {
                //小数点，直接返回
                break;
            }
            if ((length - i) % width == 0) {
                //添加空格
                sb.insert(i, separator);
            }
        }
        return sb;
    }

    /**
     * 左侧补齐
     */
    public static CharSequence leftJust(CharSequence source, int width, char fillChar) {
        return just(source, width, fillChar, true);
    }

    /**
     * 右侧补齐
     */
    public static CharSequence rightJust(CharSequence source, int width, char fillChar) {
        return just(source, width, fillChar, false);
    }

    /**
     * 补齐
     */
    public static CharSequence just(CharSequence source, int width, char fillChar, boolean left) {
        int remain = width - source.length();
        if (remain > 0) {
            StringBuilder sb = new StringBuilder(source);
            for (int i = 0; i < remain; i++) {
                if (left) {
                    sb.insert(0, fillChar);
                } else {
                    sb.append(fillChar);
                }
            }
            return sb;
        }
        return source;
    }
}
