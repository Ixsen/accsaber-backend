package de.ixsen.accsaber.database.model;

import net.bytebuddy.implementation.bind.annotation.Default;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Category extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String categoryName;
    private String description;
    private String categoryDisplayName;
    private boolean countsTowardsOverall;

    @Column(name = "player_curve_y1")
    @ColumnDefault("0.1")
    private double playerCurveY1 = 0.1;

    @Column(name = "player_curve_x1")
    @ColumnDefault("15")
    private double playerCurveX1 = 15;

    @Column(name = "player_curve_k")
    @ColumnDefault("0.4")
    private double playerCurveK = 0.4;

    @Column(name = "ap_curve_a")
    @ColumnDefault("1.036")
    private double apCurveA = 1.036;

    @Column(name = "ap_curve_b")
    @ColumnDefault("62")
    private double apCurveB = 62;

    @Column(name = "ap_curve_c")
    @ColumnDefault("10")
    private double apCurveC = 10;

    @Column(name = "ap_curve_d")
    @ColumnDefault("15.5")
    private double apCurveD = 15.5;

    @Column(name = "ap_curve_e")
    @ColumnDefault("10")
    private double apCurveE = 10;

    /**
     * Potential for the future:
     * Different calcs for different leaderboards, steeper curve for true acc, less steep for tech etc
     */

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryDisplayName() {
        return this.categoryDisplayName;
    }

    public void setCategoryDisplayName(String categoryDisplayName) {
        this.categoryDisplayName = categoryDisplayName;
    }

    public boolean isCountsTowardsOverall() {
        return this.countsTowardsOverall;
    }

    public void setCountsTowardsOverall(boolean countsTowardsOverall) {
        this.countsTowardsOverall = countsTowardsOverall;
    }

    public double getPlayerCurveY1() {
        return this.playerCurveY1;
    }

    public void setPlayerCurveY1(double playerCurveY1) {
        this.playerCurveY1 = playerCurveY1;
    }

    public double getPlayerCurveX1() {
        return this.playerCurveX1;
    }

    public void setPlayerCurveX1(double playerCurveX1) {
        this.playerCurveX1 = playerCurveX1;
    }

    public double getPlayerCurveK() {
        return this.playerCurveK;
    }

    public void setPlayerCurveK(double playerCurveK) {
        this.playerCurveK = playerCurveK;
    }

    public double getApCurveA() {
        return this.apCurveA;
    }

    public void setApCurveA(double apCurveA) {
        this.apCurveA = apCurveA;
    }

    public double getApCurveB() {
        return this.apCurveB;
    }

    public void setApCurveB(double apCurveB) {
        this.apCurveB = apCurveB;
    }

    public double getApCurveC() {
        return this.apCurveC;
    }

    public void setApCurveC(double apCurveC) {
        this.apCurveC = apCurveC;
    }

    public double getApCurveD() {
        return this.apCurveD;
    }

    public void setApCurveD(double apCurveD) {
        this.apCurveD = apCurveD;
    }

    public double getApCurveE() {
        return this.apCurveE;
    }

    public void setApCurveE(double apCurveE) {
        this.apCurveE = apCurveE;
    }
}
