function [ data ] = integr( data, chunksize )
%INTEGR Summary of this function goes here
%   Detailed explanation goes here

counter = 0;
for i = 1:size(data,1)
    counter = counter+data(i);
    
    if mod(i,chunksize) == 0
        
        for j = (i-chunksize+1):i
            data(j) = counter;
        end
        counter = 0;
    end
end

end

