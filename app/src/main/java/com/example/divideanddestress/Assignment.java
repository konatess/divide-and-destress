package com.example.divideanddestress;

import java.io.Serializable;

class Assignment implements Serializable {
    public String name;
    public short unitsTotal;
    public short unitsCompleted = 0;
    public short daysTotal;
    public short daysRemaining;
}
