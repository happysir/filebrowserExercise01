public class IconifiedTextListAdapter extends BaseAdapter {

 
	private Context mContext;

	private List<IconifiedText> mItems = new ArrayList<IconifiedText>();

	public IconifiedTextListAdapter(Context context) {
		mContext = context;
	}

	public void addItem(IconifiedText it) { mItems.add(it); }

	public void setListItems(List<IconifiedText> lit) { mItems = lit; }

  public Object getItem(int position) { return mItems.get(position); }

  public int getCount() { return mItems.size(); }

	public boolean areAllItemsSelectable() { return false; }

	public boolean isSelectable(int position) { 
		return mItems.get(position).isSelectable();
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		IconifiedTextView fileText;
	
			fileText = new IconifiedTextView(mContext, mItems.get(position));
	
			fileText = (IconifiedTextView) convertView;
			fileText.setText(mItems.get(position).getText());
			fileText.setIcon(mItems.get(position).getIcon());

		return btv;
	}
}
