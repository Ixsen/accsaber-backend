package de.ixsen.accsaber.api.dtos;

public class CategoryDto {

    private String categoryName;
    private String description;
    private String categoryDisplayName;
    private boolean countsTowardsOverall;

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
}
