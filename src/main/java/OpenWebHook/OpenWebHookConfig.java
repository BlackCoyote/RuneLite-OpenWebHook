package OpenWebHook;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("OpenWebHook")
public interface OpenWebHookConfig extends Config
{
	@ConfigItem(
		keyName = "webhooks",
		name = "Webhook URLs",
		description = "The URLs to send webhook events to, separated by a comma.",
		section = "Webhook URLs"
	)
	String webhooks();
}
