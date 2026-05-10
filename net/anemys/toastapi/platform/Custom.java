package net.anemys.toastapi.platform;

//soon
public class Custom
        implements IVersionLogic
{

    @Override
    public String[] versions()
    {
        return new String[]
                {
                        "1.16", "1.17", "1.18", "1.19", "1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4"
                };
    }
}