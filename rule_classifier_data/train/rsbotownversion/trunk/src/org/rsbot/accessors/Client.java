package org.rsbot.accessors;

import java.awt.Rectangle;

import org.rsbot.bot.input.Keyboard;
import org.rsbot.bot.input.Mouse;

public interface Client {
	
	ChatLine[] getChatLines();

	NodeList getActionDataList();

	int getBaseX();

	int getBaseY();

	Callback getCallBack();

	int getCameraPitch();

	// RenderData getCameraRenderData();
	int getCameraYaw();

	int getCamPosX();

	int getCamPosY();

	int getCamPosZ();

//	String[] getChatMessages();

//	String[] getChatNames();

//	int[] getChatTypes();

	String getCurrentPassword();

	String getCurrentUsername();

	int getDestX();

	int getDestY();

	DetailInfo getDetailInfo();

	// Render getGameRender();
	byte[][][] getGroundByteArray();

	int getGUIRSInterfaceIndex();

	int getIdleTime();

	Keyboard getKeyboard();

	int getLoginIndex();

	int getLoopCycle();

	int getMenuOptionsCount();

	int getMenuX();

	int getMenuY();

	int getMinimapAngle();

	float getMinimapOffset();

	int getMinimapScale();

	int getMinimapSetting();

	Mouse getMouse();

	//MouseWheel getMouseWheel();

	RSPlayer getMyRSPlayer();

	int getPlane();

	int getPublicChatMode();

	RSGround[][][] getRSGroundArray();

	RSGroundData[] getRSGroundDataArray();

	StatusNodeList getRSInteractingDefList();

	Rectangle[] getRSInterfaceBoundsArray();

	RSInterface[][] getRSInterfaceCache();

	NodeCache getRSInterfaceNC();

	// MRUNodes getRSItemDefMRUNodes();
	NodeCache getRSItemNodeCache();

	NodeCache getRSNPCNC();

	int getRSNPCCount();

	int[] getRSNPCIndexArray();

	RSPlayer[] getRSPlayerArray();

	int getRSPlayerCount();

	int[] getRSPlayerIndexArray();

	String getSelectedItemName();

	int getSelfInteracting();

	Settings getSettingArray();

	Signlink getSignLink();

	int[] getSkillExperiences();

	int[] getSkillExperiencesMax();

	int[] getSkillLevelMaxes();

	int[] getSkillLevels();

	TileData[] getTileData();

	boolean[] getValidRSInterfaceArray();

	boolean isFlagged();

	int isItemSelected();

	// boolean isMembers();
	boolean isMenuOpen();

	boolean isSpellSelected();

	RSItemDefFactory getRSItemDefFactory();

	void setCallback(Callback cb);

}
