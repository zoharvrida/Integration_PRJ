/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bdsm.util;

import bdsm.util.SchedulerUtil;
import bdsm.util.oracle.DateUtility;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author v00019722
 */
public class ClassConverterUtil {

    private static final Logger LOGGER = Logger.getLogger(ClassConverterUtil.class);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

    public static List getRefClass(Object sourceobject, String name) {
        Class OriginClass = sourceobject.getClass();
        List OriginMethod = new ArrayList();

        Method g = null;

        try {
            Field[] OriginField = OriginClass.getDeclaredFields();
            for (int f = 0; f < OriginField.length; f++) {
                boolean Flagget = true;
                boolean Flagset = true;
                try {
                    // check setter
                    try {
                        g = sourceobject.getClass().getMethod("set" + Character.toUpperCase(OriginField[f].getName().charAt(0)) + OriginField[f].getName().substring(1), OriginField[f].getType());
                        if (g != null) {
                            Flagset = true;
                        }
                    } catch (NoSuchMethodException ex) {
                        LOGGER.info("Object Set Exception :" + ex, ex);
                        Flagset = false;
                    }
                    // check Getter
                    try {
                        g = sourceobject.getClass().getMethod("get" + Character.toUpperCase(OriginField[f].getName().charAt(0)) + OriginField[f].getName().substring(1), new Class[0]);
                        if (g != null && Flagset) {
                            Flagget = true;
                        }
                    } catch (NoSuchMethodException ex) {
                        Flagget = false;
                    }
                    // check Boolean
                    if (Flagget == false) {
                        try {
                            g = sourceobject.getClass().getMethod("is" + Character.toUpperCase(OriginField[f].getName().charAt(0)) + OriginField[f].getName().substring(1), new Class[0]);
                            if (g != null && Flagset) {
                                Flagget = true;
                            }
                        } catch (NoSuchMethodException ex) {
                            Flagget = false;
                        }
                    }
                    if (Flagget && Flagset) // get Set Available
                    {
                        //LOGGER.info("FIELD NAme  " + OriginField[f].getName());
                        OriginMethod.add(name + "." + OriginField[f].getName());
                    }
                } catch (Exception securityException) {
                    LOGGER.info("insuficient Previliege :" + securityException, securityException);
                }
            }
        } catch (SecurityException securityException) {
            LOGGER.info("insuficient Previliege :" + securityException, securityException);
        }

        return OriginMethod;
    }

    public static Object ReferenceClass(Object sourceobject, Object destObject) {
        Class OriginClass = sourceobject.getClass();
        Class DestClass = destObject.getClass();

        LinkedList<Field> fieldOrigin = new LinkedList<Field>();
        LinkedList<Object> objOrigin = new LinkedList<Object>();
        Class<? extends Object> returnType;

        Method g = null;

        try {
            Field[] OriginField = OriginClass.getDeclaredFields();
            for (int f = 0; f < OriginField.length; f++) {
                boolean Flagget = true;
                boolean Flagset = true;
                try {
                    // check setter
                    try {
                        g = sourceobject.getClass().getMethod("set" + Character.toUpperCase(OriginField[f].getName().charAt(0)) + OriginField[f].getName().substring(1), OriginField[f].getType());
                        if (g != null) {
                            Flagset = true;
                        }
                    } catch (NoSuchMethodException ex) {
                        LOGGER.info("Object Set Exception :" + OriginField[f].getType());
                        LOGGER.info("Object Set Replicate to :" + Object.class);
                        try {
                            g = OriginClass.getMethod("set" + Character.toUpperCase(OriginField[f].getName().charAt(0)) + OriginField[f].getName().substring(1), Object.class);
                            if (g != null) {
                                Flagset = true;
                            } else {
                                Flagset = false;
                            }
                        } catch (NoSuchMethodException noSuchMethodException) {
                            Flagset = false;
                        }
                    }
                    // check Getter
                    try {
                        g = sourceobject.getClass().getMethod("get" + Character.toUpperCase(OriginField[f].getName().charAt(0)) + OriginField[f].getName().substring(1), new Class[0]);
                        if (g != null && Flagset) {
                            Flagget = true;
                        }
                    } catch (NoSuchMethodException ex) {
                        Flagget = false;
                    }
                    // check Boolean
                    if (Flagget == false) {
                        try {
                            g = sourceobject.getClass().getMethod("is" + Character.toUpperCase(OriginField[f].getName().charAt(0)) + OriginField[f].getName().substring(1), new Class[0]);
                            if (g != null && Flagset) {
                                Flagget = true;
                            }
                        } catch (NoSuchMethodException ex) {
                            Flagget = false;
                        }
                    }
                    if (Flagget && Flagset) // get Set Available
                    {
                        Object valMethod = null;
                        try {
                            returnType = g.getReturnType();
                            try {
                                valMethod = g.invoke(sourceobject, new Object[0]);
                                //LOGGER.info("VALUE :" + valMethod);
                            } catch (Exception illegalAccessException) {
                                LOGGER.debug("ILLEGAL :" + illegalAccessException, illegalAccessException);
                            }
                            if (valMethod == null) {
                                if (Number.class.isAssignableFrom(returnType)) {
                                    valMethod = 0;
                                } else if (Date.class.isAssignableFrom(returnType)) {
									String dateString = "10-Jan-1900";
                                    Date dates = new Date();
                                    try {
										dates = dateFormat.parse(dateString);
									} catch (ParseException parseException) {
										LOGGER.info("DATEPARSE FAILED :" + parseException, parseException);
                                        dates = SchedulerUtil.getTime();
                                    }
                                    valMethod = dates;
                                } else if (Boolean.class.isAssignableFrom(returnType)) {
                                    valMethod = false;
                                } else {
                                    valMethod = "";
                                }
                            }
                            try {
                                //LOGGER.info("[SOURCE] FIELD :" + OriginField[f] + " VALUE :" + valMethod);
                                fieldOrigin.addFirst(OriginField[f]);
                                //fieldOrigin.add(OriginField[f]);
                                objOrigin.addFirst(valMethod);
                                //objOrigin.add(valMethod);
                            } catch (Exception e) {
                                LOGGER.debug("EXCEPTION FIRST LOOP :" + e, e);
                            }

                        } catch (Exception e) {
                            LOGGER.debug("ERROR :" + e, e);
                            valMethod = "";
                        }
                    }
                } catch (Exception securityException) {
                    LOGGER.debug("insuficient Previliege :" + securityException, securityException);
                }
            }
            Field[] DestField = DestClass.getDeclaredFields();
            boolean FlaggetD = true;
            boolean FlagsetD = true;
            LOGGER.debug("FIELD :" + fieldOrigin);
            LOGGER.debug("VALUE :" + objOrigin);
            for (int c = 0; c < DestField.length; c++) {
                Method d = null;
                // check Getter
                try {
                    d = DestClass.getMethod("get" + Character.toUpperCase(DestField[c].getName().charAt(0)) + DestField[c].getName().substring(1), new Class[0]);
                    if (d != null) {
                        FlaggetD = true;
                    }
                } catch (NoSuchMethodException ex) {
                    FlaggetD = false;
                }
                // check Boolean
                if (FlaggetD == false) {
                    try {
                        d = DestClass.getClass().getMethod("is" + Character.toUpperCase(DestField[c].getName().charAt(0)) + DestField[c].getName().substring(1), new Class[0]);
                        if (d != null) {
                            FlaggetD = true;
                        }
                    } catch (NoSuchMethodException ex) {
                        FlaggetD = false;
                    }
                }
                try {
                    d = DestClass.getMethod("set" + Character.toUpperCase(DestField[c].getName().charAt(0)) + DestField[c].getName().substring(1), DestField[c].getType());
                    if (d != null && FlaggetD) {
                        FlagsetD = true;
                    }
                } catch (NoSuchMethodException ex) {
                    LOGGER.debug("Object NAME :" + DestField[c].getName());
                    LOGGER.debug("Object SetD Exception  :" + DestField[c].getType());
                    LOGGER.debug("Object SetD Replicate to :" + Object.class);
                    try {
                        d = DestClass.getMethod("set" + Character.toUpperCase(DestField[c].getName().charAt(0)) + DestField[c].getName().substring(1), Object.class);
                        if (d != null) {
                            FlagsetD = true;
                        } else {
                            FlagsetD = false;
                        }
                    } catch (NoSuchMethodException noSuchMethodException) {
                        LOGGER.debug("NO METHOD :" + noSuchMethodException, noSuchMethodException);
                        FlagsetD = false;
                    }
                }
                if (FlagsetD && FlaggetD) {
                    for (int fc = 0; fc < fieldOrigin.size(); fc++) {
                        if (fieldOrigin.get(fc).getName().equalsIgnoreCase(DestField[c].getName())) {
                            returnType = DestField[c].getType();
                            LOGGER.debug("METHOD :" + DestField[c].getName());
                            LOGGER.debug("SOURCEMETHOD :" + fieldOrigin.get(fc).getName());
                            LOGGER.debug("RETURN TYPE set :" + returnType);
                            LOGGER.debug("VALUE ORIGIN :" + objOrigin.get(fc));
                            try {
                                //g.invoke(sourceobject, g.getReturnType());
                                Object temporary = convertValueToType(objOrigin.get(fc), returnType);
                                LOGGER.debug("CONVERT : " + temporary==null?"":temporary.toString());
                                d.invoke(destObject, temporary);
                            } catch (Exception exception) {
                                LOGGER.debug("FAILED TO INVOKE :" + exception, exception);
                            }
                            //LOGGER.info("[DESTINATION] FIELD :" + fieldOrigin.get(fc) + " VALUE :" + objOrigin.get(fc));
                            fieldOrigin.remove(fc);
                            objOrigin.remove(fc);
                        }
                    }
                }
            }

        } catch (Exception securityException) {
            LOGGER.debug("insuficient Previliege :" + securityException, securityException);
        }
        return destObject;
    }

    public static Object MapToClass(Map Source, Object dest) {
        return MapToClass(Source, dest, false);
    }

    public static Object MapToClass(Map Source, Object dest, boolean OF) {
        Class destination = dest.getClass();
        Method g, h;
        Object valMethod;
        Class<? extends Object> returnType;

        try {
            Field[] getField = destination.getDeclaredFields();
            LOGGER.debug("MAP :" + Source);
            for (int i = 0; i < getField.length; i++) {
                //LOGGER.info("FIELD DEC :" + getField[i].getName());
                if (Source.containsKey(getField[i].getName()) || Source.containsKey(getField[i].getName().toUpperCase())) {
                    //LOGGER.debug("FIELD :" + getField[i].getName());
                    try {
                        g = destination.getMethod("set" + Character.toUpperCase(getField[i].getName().charAt(0)) + getField[i].getName().substring(1), getField[i].getType());
                        h = destination.getMethod("get" + Character.toUpperCase(getField[i].getName().charAt(0)) + getField[i].getName().substring(1), new Class[0]);

                        try {
                            valMethod = h.invoke(dest, new Object[0]);
                            LOGGER.info("VALUE ORIGIN :" + valMethod.toString());
                        } catch (Exception e) {
                            //LOGGER.debug("VALUE ORIGIN NULL ");
                            valMethod = "";
                        }
                        //g = OriginClass.getMethod("get" + Character.toUpperCase(originField[f].getName().charAt(0)) + originField[f].getName().substring(1), new Class[0]);
                        if (g != null) {
                            //LOGGER.info("METHOD :" + h);
                            returnType = getField[i].getType();
                            LOGGER.debug("FIELD :" + getField[i].getName() + " VALUE :" + Source.get(getField[i].getName()));
                            Object temporary = convertValueToType(Source.get(getField[i].getName()), returnType);
                            if (temporary == null) {
                                temporary = convertValueToType(Source.get(getField[i].getName().toUpperCase()), returnType);
                            }
                            if (!"".equalsIgnoreCase(valMethod.toString())) {
                                if (OF) {
                                   g.invoke(dest, temporary); 
                                }
                            } else {
                                g.invoke(dest, temporary);
                            }

                        }
                    } catch (Exception ex) {
                        LOGGER.debug("ERROR :" + ex, ex);
                    }
                }
            }
        } catch (SecurityException ex) {
            LOGGER.debug("iNSUFICIENT Previlege :" + ex, ex);
        }
        return destination;
    }

    public static Class MapToClass(Map Source, Object dest, String field, String mapKey) {
        Class destination = dest.getClass();
        Method g;
        Class<? extends Object> returnType;

        try {
            Field destField = null;
            Field[] getField = destination.getDeclaredFields();
            for (int i = 0; i < getField.length; i++) {
                //LOGGER.info("FIELD DEC :" + getField[i].getName());
                if (getField[i].getName().equalsIgnoreCase(field)) {
                    destField = getField[i];
                }
            }
            //LOGGER.info("FIELD :" + destField.getName());
            //System.out.println("FIELD :" + destField.getName());
            try {
                g = destination.getMethod("set" + Character.toUpperCase(destField.getName().charAt(0)) + destField.getName().substring(1), destField.getType());
                if (g != null) {
                    returnType = destField.getType();
                    Object temporary = convertValueToType(Source.get(mapKey), returnType);
                    System.out.println("TEMP :" + temporary);
                    g.invoke(dest, temporary);
                }
            } catch (Exception ex) {
                LOGGER.debug("ERROR :" + ex, ex);
            }
        } catch (SecurityException ex) {
            LOGGER.debug("iNSUFICIENT Previlege :" + ex, ex);
        }
        return destination;
    }

    public static Map ClassToMap(Object[] sourceobject, String[] Naming, String[] Prefix, Integer phase) {

        Map<String, String> destObject = new HashMap<String, String>();
        for (int obj = 0; obj < sourceobject.length; obj++) {
            Class OriginClass = sourceobject[obj].getClass();

            List FieldOrigin = new ArrayList();

            Class<? extends Object> returnType;

            Method g;
            boolean Flagget = true;
            boolean Flagset = true;
            try {
                Field[] originField = OriginClass.getDeclaredFields();
                for (int f = 0; f < originField.length; f++) {
                    // check setter
                    // LOGGER.info("[" + phase + "] Field Begin :" + originField[f]);
                    try {
                        g = OriginClass.getMethod("set" + Character.toUpperCase(originField[f].getName().charAt(0)) + originField[f].getName().substring(1), originField[f].getType());
                        if (g != null) {
                            Flagset = true;
                        }
                    } catch (NoSuchMethodException ex) {
                        LOGGER.debug("[" + phase + "]Object Set Exception :" + originField[f].getType());
                        LOGGER.debug("[" + phase + "]Object Set Replicate to :" + Object.class);
                        try {
                            g = OriginClass.getMethod("set" + Character.toUpperCase(originField[f].getName().charAt(0)) + originField[f].getName().substring(1), Object.class);
                            if (g != null) {
                                Flagset = true;
                            } else {
                                Flagset = false;
                            }
                        } catch (NoSuchMethodException noSuchMethodException) {
                            Flagset = false;
                        }
                    }
                    // check Getter
                    try {
                        g = OriginClass.getMethod("get" + Character.toUpperCase(originField[f].getName().charAt(0)) + originField[f].getName().substring(1), new Class[0]);
                        if (g != null && Flagset) {
                            Flagget = true;
                            returnType = g.getReturnType();
                            String valMethod = null;
                            //LOGGER.info("[" + phase + "]Object Name :" + Naming[obj]);
                            //LOGGER.info("[" + phase + "]Object Field :" + originField[f].getName());
                            try {
                                valMethod = g.invoke(sourceobject[obj]).toString();
                                if (valMethod == null) {
                                    valMethod = "";
                                    //LOGGER.info("Null Value");
                                } else {
                                    //LOGGER.debug("VAL NAME :" + valMethod);
                                    try {
                                        //LOGGER.info("FIELD NAME :" + originField[f].getName());
                                        //LOGGER.info("CLASS NAME :" + OriginClass.getName());
                                        if (valMethod.startsWith(OriginClass.getName())) {
                                            //LOGGER.info("RECURSIVE START");
                                            Object recMethod = g.invoke(sourceobject[obj]);
                                            //LOGGER.info("REC_VALUE :" + recMethod);

                                            Object[] classMap = {recMethod};
                                            String[] mapNaming = {originField[f].getName()};
                                            String[] nameObj = {"".equals(Naming[obj]) ? "" : Naming[obj] + "."};
                                            destObject.putAll(ClassToMap(classMap, mapNaming, nameObj, 2));
                                        }
                                    } catch (Exception ex) {
                                        LOGGER.debug("NOT RECURSIVE :" + ex, ex);
                                        if ("".equalsIgnoreCase(Prefix[obj]) && "".equalsIgnoreCase(Naming[obj])) {
                                            destObject.put(originField[f].getName(), valMethod);
                                        } else {
                                            destObject.put(Prefix[obj] + Naming[obj] + "." + originField[f].getName(), valMethod);
                                        }

                                        LOGGER.debug("NAMING MAP VALUE :" + destObject.get(Naming[obj].toString() + "." + originField[f].getName().toString()));
                                    }
                                }
                                if ("".equalsIgnoreCase(Prefix[obj]) && "".equalsIgnoreCase(Naming[obj])) {
                                    destObject.put(originField[f].getName(), valMethod);
                                } else {
                                    destObject.put(Prefix[obj] + Naming[obj] + "." + originField[f].getName(), valMethod);
                                }

                            } catch (Exception e) {
                                //LOGGER.info("ERROR :" + e, e);
                                valMethod = "";
                                if ("".equalsIgnoreCase(Prefix[obj]) && "".equalsIgnoreCase(Naming[obj])) {
                                    destObject.put(originField[f].getName(), valMethod);
                                } else {
                                    destObject.put(Prefix[obj] + Naming[obj] + "." + originField[f].getName(), valMethod);
                                    LOGGER.info("NAMING MAP Error :" + destObject.get(Prefix[obj] + Naming[obj].toString() + "." + originField[f].getName().toString()));
                                }

                            }
                        }
                    } catch (NoSuchMethodException ex) {
                        Flagget = false;
                    }
                    // check Boolean
                    if (Flagget == false) {
                        try {
                            g = OriginClass.getClass().getMethod("is" + Character.toUpperCase(originField[f].getName().charAt(0)) + originField[f].getName().substring(1), new Class[0]);
                            if (g != null && Flagset) {
                                Flagget = true;
                                returnType = g.getReturnType();
                                String valMethod = null;
                                try {
                                    valMethod = g.invoke(sourceobject[obj]).toString();
                                    if (valMethod == null) {
                                        valMethod = "";
                                    } else {
                                        Object recMethod = null;
                                        try {
                                            recMethod = g.invoke(sourceobject[obj]);
                                            valMethod = recMethod.toString();
                                        } catch (IllegalAccessException illegalAccessException) {
                                            LOGGER.info("ERROR ILLEGAL ACC :" + illegalAccessException, illegalAccessException);
                                        } catch (IllegalArgumentException illegalArgumentException) {
                                            LOGGER.info("ERROR ILLEGAL ARG :" + illegalArgumentException, illegalArgumentException);
                                        } catch (InvocationTargetException invocationTargetException) {
                                            LOGGER.info("ERROR INVOCATION :" + invocationTargetException, invocationTargetException);
                                        }
                                    }
                                    if ("".equalsIgnoreCase(Prefix[obj]) && "".equalsIgnoreCase(Naming[obj])) {
                                        destObject.put(originField[f].getName(), valMethod);
                                    } else {
                                        destObject.put(Prefix[obj] + Naming[obj] + "." + originField[f].getName(), valMethod);
                                    }

                                } catch (Exception e) {
                                    //LOGGER.info("ERROR :" + e, e);
                                    valMethod = "";
                                    if ("".equalsIgnoreCase(Prefix[obj]) && "".equalsIgnoreCase(Naming[obj])) {
                                        destObject.put(originField[f].getName(), valMethod);
                                    } else {
                                        destObject.put(Prefix[obj] + Naming[obj] + "." + originField[f].getName(), valMethod);
                                        LOGGER.info("NAMING MAP Error :" + destObject.get(Prefix[obj] + Naming[obj] + "." + originField[f].getName()));
                                    }

                                }
                            }
                        } catch (NoSuchMethodException ex) {
                            Flagget = false;
                        }
                    }
                    if (Flagget && Flagset) {
                        FieldOrigin.add(originField[f]);
                    }
                }
            } catch (Exception securityException) {
                LOGGER.info("insuficient Previliege :" + securityException, securityException);
            }
        }
        return destObject;
    }

    private static Object convertValueToType(Object value, Class<?> toType) throws Exception {
        Object convertedValue = null;

        try {
            if (value == null) {
                convertedValue = toType.cast(null);
            } else if (toType.equals(value.getClass())) {
                if ((isPrimitiveOrWrapper(toType))
                        || (String.class.isAssignableFrom(toType))
                        || (Number.class.isAssignableFrom(toType))
                        || (Date.class.isAssignableFrom(toType))
                        || (Collection.class.isAssignableFrom(toType))
                        || (Map.class.isAssignableFrom(toType))) {
                    convertedValue = value;
                }
            } else if ((Number.class.isAssignableFrom(toType))
                    && ((Number.class.isAssignableFrom(value.getClass())) || (String.class.isAssignableFrom(value.getClass())))) {
                java.lang.reflect.Constructor<?> c = toType.getConstructor(String.class);
                convertedValue = c.newInstance(value.toString());
            } else if ((Date.class.isAssignableFrom(toType)) && (String.class.isAssignableFrom(value.getClass()))) {
                java.lang.reflect.Constructor<?> c = toType.getConstructor(Long.TYPE);
                convertedValue = c.newInstance(DateUtility.DATE_TIME_FORMAT_YYYYMMDD.parse(((String) value).replace('/', '-').replace('T', ' ')).getTime());
            }

            return convertedValue;
        } catch (Exception ex) {
            LOGGER.debug(ex, ex);
            throw new Exception("'" + toType + "' is not assignable from '" + value.getClass() + "'");
        }
    }

    private static boolean isPrimitiveOrWrapper(Class<?> type) {
        if (type == null) {
            return false;
        }

        return ((type.isPrimitive()) || (ClassUtils.wrapperToPrimitive(type) != null));
    }
}
