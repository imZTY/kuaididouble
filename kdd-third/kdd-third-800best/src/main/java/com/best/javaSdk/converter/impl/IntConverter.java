package com.best.javaSdk.converter.impl;

import org.w3c.dom.Node;

import java.lang.reflect.Field;

public class IntConverter implements com.best.javaSdk.converter.Converter {
    @Override
    public Object convertXml(Node node, Field field) {
        Node firstChild = node.getFirstChild();
        return Integer.parseInt(firstChild.getNodeValue());
    }

    @Override
    public String xmlReverse(Object propValue, Field prop) {
        String propName = prop.getName();
        return com.best.javaSdk.Parser.appendXmlNode(propName, propValue.toString());
    }

    @Override
    public Object convertJson(Object value, Field field) {
        return Integer.parseInt(value.toString());
    }
}
