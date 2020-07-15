package com.example.request_thegame.Frag.login_cadastro;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.request_thegame.Interface.InterfaceCadastroUsuario;
import com.example.request_thegame.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

public class CaptarFotoFragment extends Fragment {

    private Bitmap image = null;
    private CircleImageView imageUsuario;
    private TextView btnTextCamera, btnTextGaleria;
    private Button btnProximo;
    private final int CODE_IMAGE_CAPTURE = 100;
    private final int CODE_IMAGE_GALLERY= 200;
    private InterfaceCadastroUsuario interfaceCadastro;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_captar_foto,container,false);

        imageUsuario = view.findViewById(R.id.image_usuario);
        btnTextCamera =view.findViewById(R.id.btn_text_camera);
        btnTextGaleria=view.findViewById(R.id.btn_text_galeria);
        btnProximo=view.findViewById(R.id.btn_proximo);

        checarPermissoes();

        btnTextCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(intent.resolveActivity(getContext().getPackageManager())!=null){
                        startActivityForResult(intent,CODE_IMAGE_CAPTURE);
                    }

                } else{
                    requestPermissions(new String[]{Manifest.permission.CAMERA},CODE_IMAGE_CAPTURE);
                }

            }
        });


        btnTextGaleria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                        startActivityForResult(intent, CODE_IMAGE_GALLERY);
                    }
                } else{
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},CODE_IMAGE_GALLERY);
                }
            }
        });

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (image!=null){
                    interfaceCadastro.setFotoUsuario(image);
                    getParentFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container_login,new CadastrarUsuarioFragment())
                            .commit();
                }

                else{
                    Toast.makeText(getContext(),
                            "É necessário a foto de perfil para continuar!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK){
            try{
                switch (requestCode){
                    case CODE_IMAGE_CAPTURE:
                        image = (Bitmap) data.getExtras().get("data");
                        break;

                    case CODE_IMAGE_GALLERY:
                        Uri uriImage = data.getData();
                        image  =MediaStore.Images.Media.getBitmap(getContext().getContentResolver(),uriImage);
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(image!=null){
                imageUsuario.setImageBitmap(image);
            }
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            interfaceCadastro =(InterfaceCadastroUsuario) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checarPermissoes(){

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED) {


        } else{
            requestPermissions(new String[]{Manifest.permission.CAMERA},CODE_IMAGE_CAPTURE);
        }

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {

        } else{
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},CODE_IMAGE_GALLERY);
        }
    }
}
