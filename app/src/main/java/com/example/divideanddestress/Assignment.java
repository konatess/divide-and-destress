package com.example.divideanddestress;

import java.io.Serializable;

class Assignment implements Serializable {
    public String name;
    short unitsTotal;
    short unitsCompleted = 0;
    short daysTotal;
    short daysRemaining;
}
