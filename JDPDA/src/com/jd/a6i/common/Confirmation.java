package com.jd.a6i.common;

import java.io.Serializable;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Confirmation {
	private static final String TagN = "N";

	public static boolean isBoxCode(String boxCode) {
		if (boxCode == null || "".equals(boxCode)) {
			return false;
		}
		Pattern p;
		if (boxCode.contains("-")) {
			p = Pattern.compile("^[B|T|Q|F|Z][A-Za-z0-9-]+$");
			Matcher m = p.matcher(boxCode);
			return m.matches();
		} else {
			p = Pattern.compile("^[B|T|G|F|Z][C|S]([A-Za-z0-9]{15,})$");
			Matcher m = p.matcher(boxCode);
			return m.matches();
		}

	}

	public static boolean isWaybillNo(String waybillNo) {
		if (waybillNo == null || "".equals(waybillNo)) {
			return false;
		}
		if (waybillNo.length() >= 9 && isInt(waybillNo)) {
			return true;
		} else if (isMatchReceiveWaybillNo(waybillNo)) {
			return true;
		}
		return false;
	}

	private static boolean isInt(String str) {
		Pattern p = Pattern.compile("^([0-9]*)$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	private static boolean isMatchReceiveWaybillNo(String input) {
		return isMatchReceiveWaybillNoNEW(input)
				|| isMatchReceiveWaybillNoNEW_JBD(input);
	}

	private static boolean isMatchReceiveWaybillNoNEW(String input) {
		Pattern p = Pattern.compile("^([0|9]{1}[0]{1}[0-9]{9,})([0-9]{1})$");
		Matcher m = p.matcher(input.trim());
		if (m.matches()) {
			return Long.parseLong(m.group(1)) % 7 == Long.parseLong(m.group(2));
		}
		return false;
	}

	private static boolean isMatchReceiveWaybillNoNEW_JBD(String input) {
		Pattern p = Pattern
				.compile("^([V]{1}[A|B|C|D|E|F|G|0]{1}[N|P|W|0]{1})([0-9]{9,})([0-9]{1})$");
		Matcher m = p.matcher(input.trim());
		if (m.matches()) {
			return Long.parseLong(m.group(2)) % 7 == Long.parseLong(m.group(3));
		}
		return false;
	}

	public static boolean isPackNo(String packNo) {
		if (packNo == null || "".equals(packNo)) {
			return false;
		}
		Pattern p = Pattern.compile("^[A-Za-z0-9-]+$", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(packNo);
		if (!m.matches()) {
			return false;
		}
		if (packNo.length() < 25 && packNo.indexOf(TagN) > 3) {
			return true;
		}
		if ((packNo.indexOf("-") != packNo.lastIndexOf("-"))
				&& packNo.length() < 25) {
			String[] packlists = packNo.split("-");
			p = Pattern.compile("^\\d{9,}$");
			m = p.matcher(packlists[0]);
			if (m.matches()) {
				return true;
			} else if (isMatchReceivePackageNo(packNo)) {
				return true;
			}
			return false;
		}
		return false;
	}

	/// <summary>
    /// �ж��Ƿ�Ϊ�ӻ�������
    /// </summary>
    /// <remarks>
    /// 1. �����ţ� 123456789013N1S1   123456789013N1S1HA�� HΪ�ּ����Ļ������  A������ S��ʾ�ܼ���  N��ʾ�ڼ�����
    /// 2. �����ţ� 123456789013-1-1-
    /// </remarks>
    /// <param name="input"></param>
    /// <returns></returns>
	public static boolean isMatchReceivePackageNo(String input) {
		return isMatchReceivePackageNoNew(input)
				|| isMatchReceivePackageNoNew_JBD(input);
	}

	private static boolean isMatchReceivePackageNoNew(String input) {
		String waybill = "";
		return isMatchReceivePackageNo(input, waybill);
	}

	private static boolean isMatchReceivePackageNo(String input, String waybillNo) {
		Pattern p = Pattern.compile("^([0|9]{1}[0]{1}[0-9]{10,})-(\\d{1,3})-(\\d{1,3})(|-[A-Za-z0-9]*)?$");
		Matcher m = p.matcher(input.toUpperCase().trim());
		if (m.matches()) {
			waybillNo = m.group(1).trim();// match.Groups[1].Value.Trim();

			return Long.parseLong(waybillNo.substring(0, waybillNo.length() - 1)) % 7 == Integer
					.parseInt(waybillNo.substring(waybillNo.length() - 1))
					&& Integer.parseInt(m.group(2)) <= Integer.parseInt(m.group(3));
		} else {
			p = Pattern
					.compile("^([0|9]{1}[0]{1}[0-9]{10,})N(\\d{1,3})S(\\d{1,3})(|H[A-Za-z0-9]*)?$");
			// reg = new
			// Regex("^([0|9]{1}[0]{1}[0-9]{10,})N(\\d{1,3})S(\\d{1,3})(|H[A-Za-z0-9]*)?$");
			// match = reg.Match(input.ToUpper().Trim());
			m = p.matcher(input.toUpperCase().trim());
			if (m.matches()) {
				waybillNo = m.group(1).trim();
				return Long.parseLong(waybillNo.substring(0,
						waybillNo.length() - 1)) % 7 == Integer
						.parseInt(waybillNo.substring(waybillNo.length() - 1))
						&& Integer.parseInt(m.group(2)) <= Integer.parseInt(m
								.group(3));
			}
		}
		waybillNo = "";
		return false;
	}

	private static boolean isMatchReceivePackageNoNew_JBD(String input) {
		String waybill = "";
		return isMatchReceivePackageNo_JBD(input, waybill);
	}

	private static boolean isMatchReceivePackageNo_JBD(String input,
			String waybillNo) {
		Pattern p = Pattern
				.compile("^([V]{1}[A|B|C|D|E|F|G|0]{1}[N|P|W|0]{1}[0-9]{10,})-(\\d{1,3})-(\\d{1,3})(|-[A-Za-z0-9]*)?$");
		Matcher m = p.matcher(input.toUpperCase().trim());
		if (m.matches()) {
			waybillNo = m.group(1).trim();
			return Long
					.parseLong(waybillNo.substring(3, waybillNo.length() - 4)) % 7 == Integer
					.parseInt(waybillNo.substring(waybillNo.length() - 1))
					&& Integer.parseInt(m.group(2)) <= Integer.parseInt(m
							.group(2));

		} else {
			p = Pattern
					.compile("^([V]{1}[A|B|C|D|E|F|G|0]{1}[N|P|W|0]{1}[0-9]{10,})N(\\d{1,3})S(\\d{1,3})(|H[A-Za-z0-9]*)?$");
			m = p.matcher(input.toUpperCase().trim());
			if (m.matches()) {
				waybillNo = m.group(1).trim();
				return Long.parseLong(waybillNo.substring(3,
						waybillNo.length() - 4)) % 7 == Integer
						.parseInt(waybillNo.substring(waybillNo.length() - 1))
						&& Integer.parseInt(m.group(2)) <= Integer.parseInt(m
								.group(3));
			}
		}
		waybillNo = "";
		return false;
	}

	public static boolean isSurfaceNo(String surfaceNo) {
		if (surfaceNo == null || "".equals(surfaceNo)) {
			return false;
		}
		Pattern p = Pattern.compile("^[W](\\d{10})$", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(surfaceNo);
		return m.matches();
	}
	
	
	//by whl
	public static boolean isLegalSealCarNo(String input,String regular){
		if(isLegalInput(input)){
			return isLegalExpression(input, regular);
		}
		return false;
	}
	
	public static boolean isLegalCarNo(String input,String regular){
		if(isLegalSealCarNo(input, ConstantUtils.SEAL_CAR_NO_REGULAR_EXPRESSION)){
			return false;
		}else{
			if (isLegalInput(input)) {
				return isLegalExpression(input, regular);
			}
		}
		return false;
	}
	
	public static boolean isLegalTurnoverBoxNo(String input,String regular){
		if(!isLegalInput(input)){
			return true;
		}else{
			return isLegalExpression(input, regular);
		}
	}
	
	private static boolean isLegalInput(String input){
		if(input!=null&&!"".equals(input)){
			return true;
		}
		return false;
	}
	
	public static boolean isLegalExpression(String input,String regular){
		Pattern pattern = Pattern.compile(regular,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input.toUpperCase());
		return matcher.matches();
	}
	
	public static MatchResult toMatchResult(String input,String regular){
		Pattern pattern = Pattern.compile(regular,Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(input.toUpperCase());
		return matcher.matches()?matcher.toMatchResult():null;
	}
	
	public static boolean isReceiveBoxNo(String input){
       return isReverseBoxNo(input) || isPackNo(input) || isWaybillNo(input) || isSurfaceNo(input);
    }
	
	/// <summary>
    /// ����������֤ 
    /// ��1λ��T-�˻���G-ȡ����
    /// ��2λ��C-��ͨ��S-�ݳ�Ʒ�� 
    /// 3-9λ�������ر�ţ�
    /// 10-16λ������ر�ţ�
    /// ���8λ����ˮ�š�
    /// һ��24λ��
    /// </summary>
    /// <param name="input"></param>
    /// <returns></returns>
    public static boolean isReverseBoxNo(String input){
        if (input==null||"".equals(input)){
            return false;
        }
        if (input.contains("-")){
        	return (isLegalExpression(input, "^[T|Q][A-Za-z0-9-]+$")&&toMatchResult(input, "-").groupCount()>=2);
        }
        return isLegalExpression(input, "^[T|G][C|S]([A-Za-z0-9]{15,})$");
    }
    /**
     * ��֤�Ƿ�ּ��˵���\������
     * @param input �����ַ���
     * @return
     */
    public static boolean isPackSortNumber(String input)
    {
        return isMatchPackageNo(input)
            || isMatchReceivePackageNo(input)
            || isMatchWaybillNo(input)
            || isMatchSlipNo(input)
            || isMatchReceiveWaybillNo(input);

    }
    
    /**
     * �Ƿ�ƥ�侩�������Ź���
     * ����153636597N1S1,153636597N1S1H1,153636597N1S1H,�� HΪ�ּ����Ļ������  A������ S��ʾ�ܼ���  N��ʾ�ڼ���
     * 153636597-1-1,153636597-1-1-,153636597-1-1-1      
     * @param input
     * @return
     */
    private static boolean isMatchPackageNo(String input)
    {
    	Pattern p = Pattern
				.compile("^[1-9]{1}\\d{8,18}-(\\d{1,3})-(\\d{1,3})(|-[A-Za-z0-9]{0,8})?$");
		Matcher m = p.matcher(input.trim());
		if (m.matches()) {
			return Long.parseLong(m.group(1)) <= Long.parseLong(m.group(2));
		}
		
		p = Pattern
				.compile("^[1-9]{1}\\d{8,18}N(\\d{1,3})S(\\d{1,3})(|H[A-Za-z0-9]{0,8})?$");
		m = p.matcher(input.trim());
		if (m.matches()) {
			return Long.parseLong(m.group(1)) <= Long.parseLong(m.group(2));
		}
		return false;
    }
    
    /**
     * �Ƿ�ƥ�侩���˵��Ź���
     * ����  �����˵��ţ��˵���Ϊ������ Ŀǰ9λ���֡�
     * @param input
     * @return
     */
    private static boolean isMatchWaybillNo(String input)
    {
    	Pattern p = Pattern
				.compile("^[1-9]{1}[0-9]{8,18}$");
		Matcher m = p.matcher(input.trim());
        return m.matches();
    }
    
    /**
     * �Ƿ�ƥ��ȡ�����Ź���
     * �浥�ţ�ȡ�������ɵ��浥�ţ���W +10λ��ˮ�š�һ��11λ
     * ȡ����֧��10λ���ֵ�ȡ������.
     * @param input
     * @return
     */
    private static boolean isMatchSlipNo(String input)
    {
    	Pattern p = Pattern
				.compile("^W?\\d{10}$");
		Matcher m = p.matcher(input.trim());
        return m.matches();
    }
    
    
    /// <summary>
    /// //������Ż�վ���ţ�7λ���룩�ж��Ƿ�ƥ��ּ��Ŀ�ĵ�����
    /// </summary>
    /// <param name="type">����ConfigHelper.SortingType</param>
    /// <param name="code">��Ż����</param>
    /// <param name="isBoxCode">�Ƿ������</param>
    /// <param name="message"></param>
    /// <returns></returns>
    public static final String SORTING_TYPE = "sortingType";
    public static boolean isMatchSortingType(SortingType sortingType, String code, boolean isBoxCode, StringBuffer outputMessage){
    	outputMessage.append("");
        boolean result = false;

        if (isNullOrEmpty(code)){
        	outputMessage.append("ȱ�ٱ�Ų���");
            return result;
        }

        if (isBoxCode && isBoxCode(code)){//���
            result = isMatchBox(sortingType, code, 12, outputMessage);
            outputMessage.append("���");
        }else if (isSiteNo(code) && code.length() == 7 && sortingType != SortingType.BMERCHANT){//վ��7λ���
            result = isMatchBox(sortingType, code, 3, outputMessage);
            outputMessage.append("���");
        }else if (isSiteNo(code)){//B�̼�8λ���
            result = isMatchBox(sortingType, code, 3, outputMessage);
            outputMessage.append("���");
            if (code.length() != 8 && sortingType == SortingType.BMERCHANT){
                result = false;
            }
        }

        if (isNullOrEmpty(outputMessage.toString())){
        	outputMessage.append("��Ŵ���");
        }else{
            String pre = result ? "��ȷ��" : "�����";
            outputMessage.insert(0, pre);
        }

        return result;

    }
   
   public static boolean isSiteNo(String input){
	   if(input.length()<9){
		   if(isLegalExpression(input, "^\\w+[a-zA-Z]+\\d+$")||
			  isLegalExpression(input, "^\\d+$")){
			   return true;
		   }
	   }else{
		   return false;
	   }
	   return false;
   }
    
   private static boolean isMatchBox(SortingType sortingType, String code, int index, StringBuffer outputMessage){
	   outputMessage.append("");

       String letter = code.substring(index, index+1);
       String flagLetter = getFlagLetter(sortingType, outputMessage);

       return !isNullOrEmpty(flagLetter) && flagLetter.indexOf(letter) > -1;
   }
   
   
   
   private static final String FLAG_A = "AYHRMK";

   private static final String FLAG_F = "F";
   private static final String FLAG_E = "E";

   private static final String FLAG_W = "W";
   private static final String FLAG_T = "TV";
   private static final String FLAG_S = "S";
   private static final String FLAG_K = "KI";
   private static String getFlagLetter(SortingType sortingType, StringBuffer outputMessage){
	   outputMessage.append("");
       String flagLetter = null;
       switch (sortingType){
           case SORTINGCENTER:
        	   outputMessage.append("�ּ�����");
               flagLetter = FLAG_F;
               break;
           case AFTERSALE:
        	   outputMessage.append("�ۺ�");
               flagLetter = FLAG_S;
               break;
           case WAREHOUSE:
        	   outputMessage.append("�ⷿ");
               flagLetter = FLAG_W;
               break;
           case BMERCHANT:
        	   outputMessage.append("B�̼�");
               flagLetter = FLAG_K;
               break;
           case THIRDPARTY:
        	   outputMessage.append("����");
               flagLetter = FLAG_E;
               break;
           default:
        	   outputMessage.append("");
               flagLetter = null;
               break;
       }
       return flagLetter;
   }
   
   public static boolean isNullOrEmpty(String content){
	   if(null==content||"".equals(content)){
		   return true;
	   }
	   return false;
   }
    
   /**�ּ�����ö��*/
  public enum SortingType implements Serializable{
	    /**�ִ��ּ�*/
    	WAREHOUSE(122),
    	/**�ۺ�ּ�*/
    	AFTERSALE(121),
    	/**B�̼ҷּ�*/
    	BMERCHANT(141),
    	/**��ּ����ķּ�*/
    	SORTINGCENTER(112),
    	/**�������*/
    	THIRDPARTY(131);
    	int type;
    	SortingType(int type){
    		this.type = type;
    	}
    }
}
