function [ out ] = Q(a,b,in,out)

    temp_diff = in - out;
    temp_diff_deriv = zeros(length(in),1);
    temp_diff_deriv(2:end-1) = 0.5*(temp_diff(3:end)-temp_diff(1:end-2));
    temp_diff_deriv(temp_diff_deriv<0) = 0;
    out = a*temp_diff + b*temp_diff_deriv;

end

