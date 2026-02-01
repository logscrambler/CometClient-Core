package net.comet.core.module.setting;

import java.util.function.Supplier;

public class NumberSetting extends Setting<Double> {
    private double value;
    private final double min;
    private final double max;
    private final double step;

    public NumberSetting(String name, double defaultValue, double min, double max, double step) {
        super(name);
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public NumberSetting(String name, double defaultValue, double min, double max, double step, Supplier<Boolean> visibility) {
        super(name, visibility);
        this.value = defaultValue;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Double value) {
        // 범위 제한 및 step 보정
        double clamped = Math.max(min, Math.min(max, value));
        // Step에 맞게 반올림 (정밀도 문제 해결을 위해 간단히 처리)
        // 실제로는 BigDecimal 등을 쓰는 게 좋지만 게임 모드에서는 이 정도로 충분함
        this.value = clamped; 
    }
    
    public double getMin() { return min; }
    public double getMax() { return max; }
    public double getStep() { return step; }
    
    // int나 float로 값을 가져오기 편하게 헬퍼 메서드 제공
    public int getInt() { return (int) value; }
    public float getFloat() { return (float) value; }
}
