public class Stall
{
    private int objectId;
    private int requiredLevel;

    public Stall(int objectId, int requiredLevel)
    {
        this.objectId = objectId;
        this.requiredLevel = requiredLevel;
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