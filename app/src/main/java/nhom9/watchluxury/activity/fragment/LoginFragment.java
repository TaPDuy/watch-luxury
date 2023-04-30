package nhom9.watchluxury.activity.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import nhom9.watchluxury.activity.RegisterActivity;
import nhom9.watchluxury.databinding.FragmentLoginBinding;
import nhom9.watchluxury.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {

    private LoginViewModel viewModel;

    private FragmentLoginBinding binding;

    private LoginListener listener;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        binding.setLifecycleOwner(requireActivity());
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initObserver();

        binding.btnRegister.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(requireContext(), RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void initObserver() {
        viewModel.getStatus().observe(getViewLifecycleOwner(), status -> {
            boolean res = false;

            switch (status) {
                case SUCCESS:
                    res = true;
                    break;
                case WRONG_LOGIN:
                    Toast.makeText(requireContext(), "Username or password is not correct", Toast.LENGTH_SHORT).show();
                    break;
                case USERNAME_EMPTY:
                    Toast.makeText(requireContext(), "Please enter username", Toast.LENGTH_SHORT).show();
                    break;
                case PASSWORD_EMPTY:
                    Toast.makeText(requireContext(), "Please enter password", Toast.LENGTH_SHORT).show();
                    break;
                case ERROR:
                    Toast.makeText(requireContext(), "Oops, something went wrong!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

            listener.onLoginResult(res);
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        listener = (LoginListener) context;
    }

    public interface LoginListener {
        void onLoginResult(boolean res);
    }
}
