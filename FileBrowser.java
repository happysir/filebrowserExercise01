public class FileBrowser extends ListActivity {
  
  private List<IconifiedText> directoryEntries = new ArrayList<IconifiedText>();
	private enum DISPLAYMODE{ ABSOLUTE, RELATIVE; }
	private final DISPLAYMODE displayMode = DISPLAYMODE.RELATIVE;
	private File currentDirectory = new File("/");

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		browseToRoot();
		this.setSelection(0);
	}

	private void browseToRoot() {
		browseTo(new File("/"));
    }
	
	private void upOneLevel(){
		if(this.currentDirectory.getParent() != null)
			this.browseTo(this.currentDirectory.getParentFile());
	}
	
	private void browseTo(final File aDirectory){
	
		if(this.displayMode == DISPLAYMODE.RELATIVE)
			this.setTitle(aDirectory.getAbsolutePath() + " :: " + 
					getString(R.string.app_name));
		if (aDirectory.isDirectory()){
			this.currentDirectory = aDirectory;
			fill(aDirectory.listFiles());
		}else{
			new AlertDialog.Builder(this).setTitle(" ")
			.setMessage("In this case does not support the file open operation！")
			.create().show();
		}
	}

	private void fill(File[] files) {
		this.directoryEntries.clear();
		
		this.directoryEntries.add(new IconifiedText(
				getString(R.string.current_dir), 
				getResources().getDrawable(R.drawable.folder)));		

		if(this.currentDirectory.getParent() != null)
			this.directoryEntries.add(new IconifiedText(
					getString(R.string.up_one_level), 
					getResources().getDrawable(R.drawable.uponelevel)));
		
		Drawable currentIcon = null;
		for (File currentFile : files){
			if (currentFile.isDirectory()) {
				currentIcon = getResources().getDrawable(R.drawable.folder);
			}else{
				String fileName = currentFile.getName();
		
			/*geng bu tong wen jian de tu biao she ding*/

			}
			switch (this.displayMode) {
				case ABSOLUTE:
					this.directoryEntries.add(new IconifiedText(currentFile
							.getPath(), currentIcon));
					break;
				case RELATIVE: 
					int currentPathStringLenght = this.currentDirectory.
													getAbsolutePath().length();
					this.directoryEntries.add(new IconifiedText(
							currentFile.getAbsolutePath().
							substring(currentPathStringLenght),
							currentIcon));

					break;
			}
		}
		Collections.sort(this.directoryEntries);
		
		IconifiedTextListAdapter itla = new IconifiedTextListAdapter(this);
		itla.setListItems(this.directoryEntries);		
		this.setListAdapter(itla);
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		int selectionRowID = (int) this.getSelectedItemId();
		String selectedFileString = this.directoryEntries.get(selectionRowID).getText();
		if (selectedFileString.equals(getString(R.string.current_dir))) {

			this.browseTo(this.currentDirectory);
		} else if(selectedFileString.equals(getString(R.string.up_one_level))){
			this.upOneLevel();
		} else {
			File clickedFile = null;
			switch(this.displayMode){
				case RELATIVE:
					clickedFile = new File(this.currentDirectory.getAbsolutePath() 
												+ this.directoryEntries.get(selectionRowID).getText());
					break;
				case ABSOLUTE:
					clickedFile = new File(this.directoryEntries.get(selectionRowID).getText());
					break;
			}
			if(clickedFile != null)
				this.browseTo(clickedFile);
		}
	}
	
	private boolean checkEndsWithInStringArray(String checkItsEnd, 
					String[] fileEndings){
		for(String aEnd : fileEndings){
			if(checkItsEnd.endsWith(aEnd))
				return true;
		}
		return false;
	}
}
