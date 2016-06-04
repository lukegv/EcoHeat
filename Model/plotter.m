
close all

n_total=size(outdoor_temperature_celsius,1); 

% for visualization
n=5*2880;

times = (1:n)*30/3600;

% temp_innen = zeros(n,1);
% temp_innen(1) = 18;

% for testing
% indoor_target_temperature_celsius = ones(n_total,1)*21;

% temp difference between outside and inside
temp_diff = indoor_target_temperature_celsius-outdoor_temperature_celsius;

% delta_t = 30/3600;
% lambda = 1;
% cp = 15;
% heater = 8;

% integral length
chunksize = 2*3600/30;


% derivative of temp_diff wrt time
temp_diff_deriv = zeros(n_total,1);
for i = 2:(n-1)
    temp_diff_deriv(i) = (temp_diff(i+1)-temp_diff(i-1))/(2);
end

% integrate for visualization
energy_integral = integr(energy_consumption_kwh, chunksize);
tempdiff_integral = integr(temp_diff, chunksize);

energybytempdiff = energy_integral./tempdiff_integral;

mean_integ = mean(energybytempdiff)
mean_total = mean(energy_consumption_kwh)/mean(temp_diff)
mean_point = mean(energy_consumption_kwh./temp_diff)

% total real energy consumption
total_real = sum(energy_consumption_kwh)


% derivative contrib of temp_diff to energy consump
b=0;


% for visualization
tempdiff_deriv_integral = integr(temp_diff_deriv, chunksize);


% test
b=1;

for it = 0:10
energy_est_b = energy_consumption_kwh-b*temp_diff_deriv;
for i = 1:n_total
    if energy_est_b(i) < 0
        energy_est_b(i) = 0;
    end
end

% proportional contribution of temp_diff to energy consumption
a = mean(energy_est_b./temp_diff);

c = 0;
for i = 1:n_total
    if temp_diff_deriv(i) > 0
        b = b + energy_consumption_kwh(i)/temp_diff_deriv(i);
        c = c+1;
    else
%         temp_diff_deriv(i) = 0;
    end
end
b = b/c;

end

energy_consumption_estimated = (a*temp_diff+b*temp_diff_deriv);

energy_est_integr = integr(energy_consumption_estimated, chunksize);
% deriv_integr = integr(temp_diff_deriv, chunksize);

total_est = sum(energy_consumption_estimated)

% error
energy_consumption_correction = total_real/total_est

% draw
figure 
line(times(1:n),outdoor_temperature_celsius(1:n),'Color','r')
line(times(1:n),indoor_target_temperature_celsius(1:n),'Color','b')
% line(times(1:n),tempdiff_integral(1:n),'Color','g')
ax1 = gca; % current axes
ax1.XColor = 'r';
ax1.YColor = 'r';

ax1_pos = ax1.Position; % position of first axes
ax2 = axes('Position',ax1_pos,...
    'XAxisLocation','top',...
    'YAxisLocation','right',...
    'Color','none');


% factor = mean_point*delta_t;

line(times(1:n),energy_integral(1:n),'Parent',ax2,'Color','k')
line(times(1:n),energy_est_integr(1:n),'Parent',ax2,'Color','g')
% line(times(1:n),tempdiff_deriv_integral(1:n),'Parent',ax2,'Color','y')
