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
    /// 判断是否为接货包裹号
    /// </summary>
    /// <remarks>
    /// 1. 包裹号： 123456789013N1S1   123456789013N1S1HA。 H为分拣中心滑道编号  A具体编号 S表示总件数  N表示第几件。
    /// 2. 包裹号： 123456789013-1-1-
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
    /// 逆向箱子验证 
    /// 第1位：T-退货，G-取件；
    /// 第2位：C-普通，S-奢侈品； 
    /// 3-9位：出发地编号；
    /// 10-16位：到达地编号；
    /// 最后8位：流水号。
    /// 一共24位。
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
     * 验证是否分拣运单号\包裹号
     * @param input 输入字符串
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
     * 是否匹配京东包裹号规则
     * 规则：153636597N1S1,153636597N1S1H1,153636597N1S1H,。 H为分拣中心滑道编号  A具体编号 S表示总件数  N表示第几件
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
     * 是否匹配京东运单号规则
     * 规则：  京东运单号：运单号为订单号 目前9位数字。
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
     * 是否匹配取件单号规则
     * 面单号（取件单生成的面单号）：W +10位流水号。一共11位
     * 取件单支持10位数字的取件单号.
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
    /// //依据箱号或站点编号（7位编码）判断是否匹配分拣的目的的类型
    /// </summary>
    /// <param name="type">类型ConfigHelper.SortingType</param>
    /// <param name="code">编号或箱号</param>
    /// <param name="isBoxCode">是否是箱号</param>
    /// <param name="message"></param>
    /// <returns></returns>
    public static final String SORTING_TYPE = "sortingType";
    public static boolean isMatchSortingType(SortingType sortingType, String code, boolean isBoxCode, StringBuffer outputMessage){
    	outputMessage.append("");
        boolean result = false;

        if (isNullOrEmpty(code)){
        	outputMessage.append("缺少编号参数");
            return result;
        }

        if (isBoxCode && isBoxCode(code)){//箱号
            result = isMatchBox(sortingType, code, 12, outputMessage);
            outputMessage.append("箱号");
        }else if (isSiteNo(code) && code.length() == 7 && sortingType != SortingType.BMERCHANT){//站点7位编号
            result = isMatchBox(sortingType, code, 3, outputMessage);
            outputMessage.append("编号");
        }else if (isSiteNo(code)){//B商家8位编号
            result = isMatchBox(sortingType, code, 3, outputMessage);
            outputMessage.append("编号");
            if (code.length() != 8 && sortingType == SortingType.BMERCHANT){
                result = false;
            }
        }

        if (isNullOrEmpty(outputMessage.toString())){
        	outputMessage.append("编号错误");
        }else{
            String pre = result ? "正确的" : "错误的";
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
        	   outputMessage.append("分拣中心");
               flagLetter = FLAG_F;
               break;
           case AFTERSALE:
        	   outputMessage.append("售后");
               flagLetter = FLAG_S;
               break;
           case WAREHOUSE:
        	   outputMessage.append("库房");
               flagLetter = FLAG_W;
               break;
           case BMERCHANT:
        	   outputMessage.append("B商家");
               flagLetter = FLAG_K;
               break;
           case THIRDPARTY:
        	   outputMessage.append("三方");
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
    
   /**分拣类型枚举*/
  public enum SortingType implements Serializable{
	    /**仓储分拣*/
    	WAREHOUSE(122),
    	/**售后分拣*/
    	AFTERSALE(121),
    	/**B商家分拣*/
    	BMERCHANT(141),
    	/**跨分拣中心分拣*/
    	SORTINGCENTER(112),
    	/**三方验货*/
    	THIRDPARTY(131);
    	int type;
    	SortingType(int type){
    		this.type = type;
    	}
    }
}
