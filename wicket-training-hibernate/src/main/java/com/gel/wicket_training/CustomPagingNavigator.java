package com.gel.wicket_training;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigationIncrementLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigationLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.data.DataView;
 
public class CustomPagingNavigator extends Panel {
 
  public static final String NAVIGATION_ID = "navigation";
  public static final List<Integer> DEFAULT_ITEMS_PER_PAGE_VALUES = Arrays.asList(5, 25, 50);
 
  private PagingNavigation pagingNavigation;
  private final DataView<?> dataView;
  private final IPagingLabelProvider labelProvider;
  private final List<Integer> itemsPerPageValues;
  private WebMarkupContainer pagingLinksContainer;
 
  public CustomPagingNavigator(final String id, final DataView<?> dataView) {
      this(id, dataView, null, DEFAULT_ITEMS_PER_PAGE_VALUES);
  }
 
  public CustomPagingNavigator(final String id, final DataView<?> dataView, List<Integer> itemsPerPageValues) {
      this(id, dataView, null, itemsPerPageValues);
  }
 
  public CustomPagingNavigator(final String id, final DataView<?> dataView, final IPagingLabelProvider labelProvider) {
      this(id, dataView, labelProvider, DEFAULT_ITEMS_PER_PAGE_VALUES);
  }
 
  public CustomPagingNavigator(final String id, final DataView<?> dataView, final IPagingLabelProvider labelProvider,
          List<Integer> itemsPerPageValues) {
      super(id);
      this.dataView = dataView;
      this.labelProvider = labelProvider;
      this.itemsPerPageValues = itemsPerPageValues;
 
      addContainerWithPagingLinks();
      addLinksChangingItemsPerPageNumber();
  }
 
  @Override
  public boolean isVisible() {
      return dataView.getItemCount() > 0;
  }
 
  private void addContainerWithPagingLinks() {
 
      pagingLinksContainer = new WebMarkupContainer("pagingLinksContainer") {
          @Override
          public boolean isVisible() {
              return dataView.getPageCount() > 1;
          }
      };
 
      pagingNavigation = newNavigation(dataView, labelProvider);
      pagingLinksContainer.add(pagingNavigation);
 
      // Add additional page links
      pagingLinksContainer.add(newPagingNavigationLink("first", dataView, 0).add(
              new TitleAppender("PagingNavigator.first")));
      pagingLinksContainer.add(newPagingNavigationIncrementLink("prev", dataView, -1).add(
              new TitleAppender("PagingNavigator.previous")));
      pagingLinksContainer.add(newPagingNavigationIncrementLink("next", dataView, 1).add(
              new TitleAppender("PagingNavigator.next")));
      pagingLinksContainer.add(newPagingNavigationLink("last", dataView, -1).add(
              new TitleAppender("PagingNavigator.last")));
 
      add(pagingLinksContainer);
  }
 
  protected PagingNavigation newNavigation(final IPageable pageable, final IPagingLabelProvider labelProvider) {
      return new PagingNavigation(NAVIGATION_ID, pageable, labelProvider);
  }
 
  protected AbstractLink newPagingNavigationIncrementLink(String id, IPageable pageable, int increment) {
      return new PagingNavigationIncrementLink<Void>(id, pageable, increment);
  }
 
  protected AbstractLink newPagingNavigationLink(String id, IPageable pageable, int pageNumber) {
      return new PagingNavigationLink<Void>(id, pageable, pageNumber);
  }
 
  private void addLinksChangingItemsPerPageNumber() {
      ListView<Integer> itemsPerPageList = new ListView<Integer>("itemsPerPage", itemsPerPageValues) {
          @Override
          protected void populateItem(ListItem<Integer> item) {
              Link<Void> itemPerPageLink = new ItemPerPageLink<Void>("itemPerPageLink", dataView,
                      pagingLinksContainer, item.getModelObject());
              itemPerPageLink.add(new Label("itemsValue", item.getModel()));
              item.add(itemPerPageLink);
          }
      };
 
      add(itemsPerPageList);
  }
 
  public final PagingNavigation getPagingNavigation() {
      return pagingNavigation;
  }
 
  private final class TitleAppender extends Behavior {
      private static final long serialVersionUID = 1L;
 
      private final String resourceKey;
 
      public TitleAppender(String resourceKey) {
          this.resourceKey = resourceKey;
      }
 
      @Override
      public void onComponentTag(Component component, ComponentTag tag) {
          tag.put("title", CustomPagingNavigator.this.getString(resourceKey));
      }
  }
}