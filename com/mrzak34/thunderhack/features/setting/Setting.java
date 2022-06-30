//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\38096\Downloads\Minecraft-Deobfuscator3000-master\1.12 stable mappings"!

//Decompiled by Procyon!

package com.mrzak34.thunderhack.features.setting;

import java.util.function.*;
import com.mrzak34.thunderhack.features.*;
import com.mrzak34.thunderhack.event.events.*;
import net.minecraftforge.common.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class Setting<T>
{
    private final String name;
    private T defaultValue;
    private T defaultValueY;
    private T defaultValueX;
    private T plannedValueX;
    private T plannedValueY;
    private T valueX;
    private T valueY;
    private T value;
    private T plannedValue;
    private T min;
    private T max;
    private Setting<Parent> parent;
    private T red;
    private T green;
    private T blue;
    private T alpha;
    private boolean hasRestriction;
    private Predicate<T> visibility;
    private String description;
    private Feature feature;
    
    public Setting(final String name, final T defaultValue) {
        this.parent = null;
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.plannedValue = defaultValue;
        this.description = "";
    }
    
    public Setting(final String name, final T defaultValue, final String description) {
        this.parent = null;
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.plannedValue = defaultValue;
        this.description = description;
    }
    
    public Setting(final String name, final T defaultValue, final T min, final T max, final String description) {
        this.parent = null;
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.plannedValue = defaultValue;
        this.description = description;
        this.hasRestriction = true;
    }
    
    public Setting(final String name, final T defaultValue, final T min, final T max) {
        this.parent = null;
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.plannedValue = defaultValue;
        this.description = "";
        this.hasRestriction = true;
    }
    
    public Setting(final String name, final T defaultValueX, final T defaultValueY) {
        this.parent = null;
        this.name = name;
        this.defaultValueY = defaultValueY;
        this.defaultValueX = defaultValueX;
        this.valueY = defaultValueY;
        this.valueX = defaultValueX;
        this.plannedValueX = defaultValueX;
        this.plannedValueY = defaultValueY;
        this.description = "";
        this.hasRestriction = true;
    }
    
    public Setting(final String name, final T red, final T green, final T blue, final T alpha, final T defaultValue) {
        this.parent = null;
        this.name = name;
        this.defaultValue = defaultValue;
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }
    
    public Setting(final String name, final T defaultValue, final T min, final T max, final Predicate<T> visibility, final String description) {
        this.parent = null;
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.plannedValue = defaultValue;
        this.visibility = visibility;
        this.description = description;
        this.hasRestriction = true;
    }
    
    public Setting(final String name, final T defaultValue, final T min, final T max, final Predicate<T> visibility) {
        this.parent = null;
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.plannedValue = defaultValue;
        this.visibility = visibility;
        this.description = "";
        this.hasRestriction = true;
    }
    
    public Setting(final String name, final T defaultValue, final Predicate<T> visibility) {
        this.parent = null;
        this.name = name;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        this.visibility = visibility;
        this.plannedValue = defaultValue;
    }
    
    public String getName() {
        return this.name;
    }
    
    public T getValue() {
        return this.value;
    }
    
    public void setValue(final T value) {
        this.setPlannedValue(value);
        if (this.hasRestriction) {
            if (((Number)this.min).floatValue() > ((Number)value).floatValue()) {
                this.setPlannedValue(this.min);
            }
            if (((Number)this.max).floatValue() < ((Number)value).floatValue()) {
                this.setPlannedValue(this.max);
            }
        }
        final ClientEvent event = new ClientEvent(this);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) {
            this.value = this.plannedValue;
        }
        else {
            this.plannedValue = this.value;
        }
    }
    
    public T getPlannedValue() {
        return this.plannedValue;
    }
    
    public void setPlannedValue(final T value) {
        this.plannedValue = value;
    }
    
    public T getMin() {
        return this.min;
    }
    
    public void setMin(final T min) {
        this.min = min;
    }
    
    public T getMax() {
        return this.max;
    }
    
    public void setMax(final T max) {
        this.max = max;
    }
    
    public void setValueNoEvent(final T value) {
        this.setPlannedValue(value);
        if (this.hasRestriction) {
            if (((Number)this.min).floatValue() > ((Number)value).floatValue()) {
                this.setPlannedValue(this.min);
            }
            if (((Number)this.max).floatValue() < ((Number)value).floatValue()) {
                this.setPlannedValue(this.max);
            }
        }
        this.value = this.plannedValue;
    }
    
    public Feature getFeature() {
        return this.feature;
    }
    
    public void setFeature(final Feature feature) {
        this.feature = feature;
    }
    
    public int getEnum(final String input) {
        for (int i = 0; i < this.value.getClass().getEnumConstants().length; ++i) {
            final Enum e = (Enum)this.value.getClass().getEnumConstants()[i];
            if (e.name().equalsIgnoreCase(input)) {
                return i;
            }
        }
        return -1;
    }
    
    public void setEnumValue(final String value) {
        for (final Enum e : (Enum[])((Enum)this.value).getClass().getEnumConstants()) {
            if (e.name().equalsIgnoreCase(value)) {
                this.value = (T)e;
            }
        }
    }
    
    public String currentEnumName() {
        return EnumConverter.getProperName((Enum)this.value);
    }
    
    public int currentEnum() {
        return EnumConverter.currentEnum((Enum)this.value);
    }
    
    public void increaseEnum() {
        this.plannedValue = (T)EnumConverter.increaseEnum((Enum)this.value);
        final ClientEvent event = new ClientEvent(this);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) {
            this.value = this.plannedValue;
        }
        else {
            this.plannedValue = this.value;
        }
    }
    
    public void naoborotEnum() {
        this.plannedValue = (T)EnumConverter.naoborot((Enum)this.value);
        final ClientEvent event = new ClientEvent(this);
        MinecraftForge.EVENT_BUS.post((Event)event);
        if (!event.isCanceled()) {
            this.value = this.plannedValue;
        }
        else {
            this.plannedValue = this.value;
        }
    }
    
    public void increaseEnumNoEvent() {
        this.value = (T)EnumConverter.increaseEnum((Enum)this.value);
    }
    
    public String getType() {
        if (this.isEnumSetting()) {
            return "Enum";
        }
        if (this.isColorSetting()) {
            return "ColorSetting";
        }
        if (this.isPositionSetting()) {
            return "PositionSetting";
        }
        if (this.isBlocklist()) {
            return "BlockListSetting";
        }
        return this.getClassName(this.defaultValue);
    }
    
    public <T> String getClassName(final T value) {
        return value.getClass().getSimpleName();
    }
    
    public String getDescription() {
        if (this.description == null) {
            return "";
        }
        return this.description;
    }
    
    public boolean isNumberSetting() {
        return this.value instanceof Double || this.value instanceof Integer || this.value instanceof Short || this.value instanceof Long || this.value instanceof Float;
    }
    
    public boolean isInteger() {
        return this.value instanceof Integer;
    }
    
    public boolean isFloat() {
        return this.value instanceof Float;
    }
    
    public boolean isEnumSetting() {
        return !this.isPositionSetting() && !this.isNumberSetting() && !(this.value instanceof PositionSetting) && !(this.value instanceof String) && !(this.value instanceof BlockListSetting) && !(this.value instanceof ColorSetting) && !(this.value instanceof Parent) && !(this.value instanceof Bind) && !(this.value instanceof SubBind) && !(this.value instanceof Character) && !(this.value instanceof Boolean);
    }
    
    public boolean isStringSetting() {
        return this.value instanceof String;
    }
    
    public boolean isColorSetting() {
        return this.value instanceof ColorSetting;
    }
    
    public boolean isPositionSetting() {
        return this.value instanceof PositionSetting;
    }
    
    public boolean isBlocklist() {
        return this.value instanceof BlockListSetting;
    }
    
    public T getDefaultValue() {
        return this.defaultValue;
    }
    
    public String getValueAsString() {
        return this.value.toString();
    }
    
    public boolean hasRestriction() {
        return this.hasRestriction;
    }
    
    public void setVisibility(final Predicate<T> visibility) {
        this.visibility = visibility;
    }
    
    public Setting<T> withParent(final Setting<Parent> parent) {
        this.parent = parent;
        return this;
    }
    
    public boolean hasParent() {
        return this.parent != null;
    }
    
    public Setting<Parent> getParent() {
        return this.parent;
    }
    
    public boolean isVisible() {
        return (this.parent == null || this.parent.getValue().isExtended()) && (this.visibility == null || this.visibility.test(this.getValue()));
    }
}
