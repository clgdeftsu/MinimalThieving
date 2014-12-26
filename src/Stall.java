public class Stall
{
    private String name;
    private int objectId;
    private int requiredLevel;

    public Stall(String name, int objectId, int requiredLevel)
    {
        this.name = name;
        this.objectId = objectId;
        this.requiredLevel = requiredLevel;
    }

    public String getName()
    {
        return name;
    }

    public int getObjectId()
    {
        return objectId;
    }

    public int getRequiredLevel()
    {
        return requiredLevel;
    }
}