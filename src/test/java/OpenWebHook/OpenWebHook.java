package OpenWebHook;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class OpenWebHook
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(OpenWebHookPlugin.class);
		RuneLite.main(args);
	}
}