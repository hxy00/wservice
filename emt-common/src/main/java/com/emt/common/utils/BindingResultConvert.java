package com.emt.common.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dsj on 2017/3/27.
 *
 */
public class BindingResultConvert
{
    public static List<Map<String, Object>> BindingResultToListMap(BindingResult result)
    {
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        if (result.hasErrors())
        {
            List<FieldError> errorList = result.getFieldErrors();
            for (FieldError error : errorList)
            {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(error.getField(), error.getDefaultMessage());
                maps.add(map);
            }
        }
        return maps;
    }


    public static List<String> BindingResultToList(BindingResult result)
    {
        List<String> _list= new ArrayList<String>();
        if (result.hasErrors())
        {
            List<FieldError> errorList = result.getFieldErrors();
            for (FieldError error : errorList)
            {
                _list.add(error.getDefaultMessage());
            }
        }
        return _list;
    }
}
