package com.eclipsesource.megit.plugin;

import java.util.List;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.workbench.IPresentationEngine;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.osgi.service.event.Event;

import jakarta.inject.Inject;

public class LifeCycle {

	private static final String FIRST_RUN_TAG = "firstRunTag";
	private static final String EDITOR_AREA = "org.eclipse.ui.editorss";

	@Optional
	@Inject
	public void minimizeEditorAreaOnAppStartup(
			@UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) final Event event, EModelService modelService,
			MApplication app) {
		MUIElement editorArea = modelService.find(EDITOR_AREA, app);
		if (shouldMinimize(editorArea)) {
			editorArea.getTags().add(IPresentationEngine.MINIMIZED);
			editorArea.getTags().add(FIRST_RUN_TAG);
		}
	}

	private boolean shouldMinimize(MUIElement element) {
		if (element != null) {
			List<String> tags = element.getTags();
			return !tags.contains(FIRST_RUN_TAG) && !tags.contains(IPresentationEngine.MINIMIZED);
		}
		return false;
	}
}
