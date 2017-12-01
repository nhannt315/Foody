package nhannt.foody.screen.addplace;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.VideoView;

import java.util.ArrayList;

import nhannt.foody.R;
import nhannt.foody.data.model.AddMenuModel;
import nhannt.foody.data.model.DishModel;
import nhannt.foody.data.model.MenuModel;
import nhannt.foody.data.model.PlaceUtil;
import nhannt.foody.utils.Utils;

public class AddPlaceActivity extends AppCompatActivity
    implements View.OnClickListener, AddPlaceContract.View, Spinner.OnItemSelectedListener {
    private static final int REQUEST_CODE_CAMERA = 1;
    private static final int REQUEST_CODE_VIDEO = 2;
    private static final int REQUEST_CODE_IMG_1 = 111;
    private static final int REQUEST_CODE_IMG_2 = 112;
    private static final int REQUEST_CODE_IMG_3 = 113;
    private static final int REQUEST_CODE_IMG_4 = 114;
    private static final int REQUEST_CODE_IMG_5 = 115;
    private static final int REQUEST_CODE_IMG_6 = 116;
    private AddPlaceContract.Presenter mPresenter;
    private Toolbar mToolbar;
    private Button mBtnOpenTime, mBtnCloseTime;
    private Spinner mSpinnerArea, mSpinnerMenu;
    private ImageView mImgAddPlace1, mImgAddPlace2, mImgAddPlace3, mImgAddPlace4, mImgAddPlace5,
        mImgAddPlace6, mImageVideo;
    private VideoView mVideoView;
    private LinearLayout mLLUtils, mLLBranchContainer, mLLMenuContainer;
    private String mOpenTime = "8:00", mCloseTime = "18:00";
    private ArrayList<MenuModel> mMenuModelList;
    private ArrayList<String> mAreaList, mMenuStrList;
    private ArrayList<String> mSelectedUtils, mBranchList;
    private ArrayList<AddMenuModel> mAddMenuModelList;
    private ImageView mImgTemp;
    private ArrayList<Bitmap> mBitmapTakenList;
    private ArrayList<Uri> mPlaceImgUriList;
    private Uri mVideoSelectedUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        mPresenter = new AddPlacePresenter();
        setupToolbar();
        initViews();
        initEvents();
        mBranchList = new ArrayList<>();
        mAddMenuModelList = new ArrayList<>();
        mBitmapTakenList = new ArrayList<>();
        mPlaceImgUriList = new ArrayList<>();
        cloneBranchView();
        mPresenter.getAreaList();
        mPresenter.getMenuList();
        mPresenter.getListUtilities();
    }

    private void cloneMenuView() {
        final View view = getLayoutInflater().inflate(R.layout.layout_clone_menu, null);
        final Button btnAddMenu = view.findViewById(R.id.btn_add_dish_menu);
        final Button btnRemoveMenu = view.findViewById(R.id.btn_remove_dish_menu);
        final Spinner spinnerMenu = view.findViewById(R.id.spinner_menu);
        final EditText edtDishName = view.findViewById(R.id.edt_dish_name);
        final EditText edtDishPrice = view.findViewById(R.id.edt_dish_price);
        final ImageView imageDish = view.findViewById(R.id.img_dish_item);
        mImgTemp = imageDish;
        ArrayAdapter<String> menuAdapter = new ArrayAdapter<String>(
            AddPlaceActivity.this,
            android.R.layout.simple_list_item_1,
            mMenuStrList
        );
        imageDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera, REQUEST_CODE_CAMERA);
            }
        });
        spinnerMenu.setAdapter(menuAdapter);
        mLLMenuContainer.addView(view);
        btnRemoveMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMenuModel addMenuModel = (AddMenuModel) btnAddMenu.getTag();
                int position = mAddMenuModelList.indexOf(addMenuModel);
                mAddMenuModelList.remove(position);
                mBitmapTakenList.remove(position);
                mLLMenuContainer.removeView(view);
            }
        });
        btnAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dishName = edtDishName.getText().toString().trim();
                String dishPrice = edtDishPrice.getText().toString().trim();
                if (dishName.isEmpty() || dishPrice.isEmpty() || imageDish.getTag() == null) {
                    Utils.showErrorDialog(AddPlaceActivity.this, getString(R.string.error),
                        getString(R.string.please_fill_all));
                    return;
                }
                cloneMenuView();
                v.setVisibility(View.GONE);
                btnRemoveMenu.setVisibility(View.VISIBLE);
                int position = spinnerMenu.getSelectedItemPosition();
                String imageName = Utils.getCurrentTimeInMills() + ".jpg";
                String menuCode = mMenuModelList.get(position).getMathucdon();
                DishModel dishModel = new DishModel();
                dishModel.setTenmon(dishName);
                dishModel.setGiatien(Long.parseLong(dishPrice));
                dishModel.setHinhanh(imageName);
                AddMenuModel addMenuModel = new AddMenuModel();
                addMenuModel.setMenuCode(menuCode);
                addMenuModel.setDishModel(dishModel);
                v.setTag(addMenuModel);
                mAddMenuModelList.add(addMenuModel);
            }
        });
    }

    private void cloneBranchView() {
        final View view = getLayoutInflater().inflate(R.layout.layout_clone_branch, null);
        final ImageView imageButton = view.findViewById(R.id.img_add_branch);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editTextBranch = view.findViewById(R.id.edt_branch_address);
                if (editTextBranch.getText().toString().trim().isEmpty()) {
                    Utils.showErrorDialog(AddPlaceActivity.this, getString(R.string.error),
                        getString(R.string.please_fill_all));
                    return;
                }
                imageButton.setVisibility(View.GONE);
                editTextBranch.setFocusable(false);
                ImageView imgRemoveBranch = view.findViewById(R.id.img_remove_branch);
                imgRemoveBranch.setVisibility(View.VISIBLE);
                imgRemoveBranch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mBranchList.remove(editTextBranch.getText().toString());
                        mLLBranchContainer.removeView(view);
                    }
                });
                mBranchList.add(editTextBranch.getText().toString());
                cloneBranchView();
            }
        });
        mLLBranchContainer.addView(view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri uri = null;
            switch (requestCode) {
                case REQUEST_CODE_CAMERA:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    mImgTemp.setImageBitmap(bitmap);
                    mImgTemp.setTag(new Boolean(true));
                    mBitmapTakenList.add(bitmap);
                    break;
                case REQUEST_CODE_IMG_1:
                    uri = data.getData();
                    mImgAddPlace1.setImageURI(uri);
                    mPlaceImgUriList.add(uri);
                    break;
                case REQUEST_CODE_IMG_2:
                    uri = data.getData();
                    mImgAddPlace2.setImageURI(uri);
                    mPlaceImgUriList.add(uri);
                    break;
                case REQUEST_CODE_IMG_3:
                    uri = data.getData();
                    mImgAddPlace3.setImageURI(uri);
                    mPlaceImgUriList.add(uri);
                    break;
                case REQUEST_CODE_IMG_4:
                    uri = data.getData();
                    mImgAddPlace4.setImageURI(uri);
                    mPlaceImgUriList.add(uri);
                    break;
                case REQUEST_CODE_IMG_5:
                    uri = data.getData();
                    mImgAddPlace5.setImageURI(uri);
                    mPlaceImgUriList.add(uri);
                    break;
                case REQUEST_CODE_IMG_6:
                    uri = data.getData();
                    mImgAddPlace6.setImageURI(uri);
                    mPlaceImgUriList.add(uri);
                    break;
                case REQUEST_CODE_VIDEO:
                    uri = data.getData();
                    mVideoView.setVideoURI(uri);
                    mImageVideo.setVisibility(View.GONE);
                    mVideoSelectedUri = uri;
                    MediaController mediaController = new MediaController(this);
                    mediaController.setAnchorView(mVideoView);
                    mVideoView.setMediaController(mediaController);
                    break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.setView(this);
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    private void initEvents() {
        mBtnCloseTime.setOnClickListener(this);
        mBtnOpenTime.setOnClickListener(this);
        mSpinnerArea.setOnItemSelectedListener(this);
        mImgAddPlace1.setOnClickListener(this);
        mImgAddPlace2.setOnClickListener(this);
        mImgAddPlace3.setOnClickListener(this);
        mImgAddPlace4.setOnClickListener(this);
        mImgAddPlace5.setOnClickListener(this);
        mImgAddPlace6.setOnClickListener(this);
        mImageVideo.setOnClickListener(this);
    }

    private void initViews() {
        mBtnOpenTime = findViewById(R.id.btn_open_time);
        mBtnCloseTime = findViewById(R.id.btn_close_time);
        mSpinnerArea = findViewById(R.id.spinner_area);
        mImgAddPlace1 = findViewById(R.id.img_add_place_1);
        mImgAddPlace2 = findViewById(R.id.img_add_place_2);
        mImgAddPlace3 = findViewById(R.id.img_add_place_3);
        mImgAddPlace4 = findViewById(R.id.img_add_place_4);
        mImgAddPlace5 = findViewById(R.id.img_add_place_5);
        mImgAddPlace6 = findViewById(R.id.img_add_place_6);
        mImageVideo = findViewById(R.id.add_video_dish_img);
        mVideoView = findViewById(R.id.add_video_dish);
        mLLUtils = findViewById(R.id.ll_utils);
        mLLBranchContainer = findViewById(R.id.ll_branch_container);
        mLLMenuContainer = findViewById(R.id.ll_menu_container);
        // Set time
        mBtnOpenTime.setText(getString(R.string.open_time, mOpenTime));
        mBtnCloseTime.setText(getString(R.string.close_time, mCloseTime));
    }

    private void setupToolbar() {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_open_time:
                showDateTimePickerDialog(true);
                break;
            case R.id.btn_close_time:
                showDateTimePickerDialog(false);
                break;
            case R.id.img_add_place_1:
                openGallery("image/*", REQUEST_CODE_IMG_1);
                break;
            case R.id.img_add_place_2:
                openGallery("image/*", REQUEST_CODE_IMG_2);
                break;
            case R.id.img_add_place_3:
                openGallery("image/*", REQUEST_CODE_IMG_3);
                break;
            case R.id.img_add_place_4:
                openGallery("image/*", REQUEST_CODE_IMG_4);
                break;
            case R.id.img_add_place_5:
                openGallery("image/*", REQUEST_CODE_IMG_5);
                break;
            case R.id.img_add_place_6:
                openGallery("image/*", REQUEST_CODE_IMG_6);
                break;
            case R.id.add_video_dish_img:
                openGallery("video/*", REQUEST_CODE_VIDEO);
                break;
        }
    }

    private void openGallery(String type, int requestCode) {
        Intent intentGallery = new Intent();
        intentGallery.setType(type);
        intentGallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
            Intent.createChooser(intentGallery, getString(R.string.select_image)),
            requestCode);
    }

    private void showDateTimePickerDialog(final boolean isOpenTime) {
        int hour;
        if (isOpenTime)
            hour = 8;
        else
            hour = 18;
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
            new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    if (isOpenTime) {
                        mOpenTime = hourOfDay + ":" + minute;
                        mBtnOpenTime.setText(getString(R.string.open_time, mOpenTime));
                    } else {
                        mCloseTime = hourOfDay + ":" + minute;
                        mBtnCloseTime.setText(getString(R.string.close_time, mCloseTime));
                    }
                }
            }, hour, 0, true);
        timePickerDialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void setPlaceList(ArrayList<String> placeList) {
        ArrayAdapter<String> areaAdapter = new ArrayAdapter<String>(
            AddPlaceActivity.this,
            android.R.layout.simple_list_item_1,
            placeList
        );
        mAreaList = placeList;
        mSpinnerArea.setAdapter(areaAdapter);
    }

    @Override
    public void setMenuList(ArrayList<MenuModel> menuList) {
        mMenuModelList = menuList;
        mMenuStrList = new ArrayList<>();
        for (MenuModel menuModel : menuList) {
            mMenuStrList.add(menuModel.getTenthucdon());
        }
        cloneMenuView();
    }

    @Override
    public void setListUtilities(ArrayList<PlaceUtil> listUtilities) {
        mSelectedUtils = new ArrayList<>();
        for (PlaceUtil placeUtil : listUtilities) {
            CheckBox checkBox = new CheckBox(AddPlaceActivity.this);
            checkBox.setText(placeUtil.getTentienich());
            checkBox.setTag(placeUtil.getMatienich());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String selected = buttonView.getTag().toString();
                    if (isChecked) {
                        if (!mSelectedUtils.contains(selected))
                            mSelectedUtils.add(selected);
                    } else {
                        mSelectedUtils.remove(selected);
                    }
                }
            });
            mLLUtils.addView(checkBox, new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner_area:
                break;
            case R.id.spinner_menu:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
